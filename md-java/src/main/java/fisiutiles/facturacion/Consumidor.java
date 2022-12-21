package fisiutiles.facturacion;

import com.google.gson.Gson;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.Session;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class Consumidor implements Runnable {
    
    public static void main(String[] args) {
        new Consumidor().consumirMensajeAdministracionDeInventarioYReserva();
    }

    private Properties ps;

    public Consumidor() {
        this.ps = new PropertiesReader().getProperties();
    }

    private void consumirMensajeAdministracionDeInventarioYReserva() {
        while (true) {
            ConnectionFactory cf = new ActiveMQConnectionFactory(ps.getProperty("URL"));

            try ( Connection con = cf.createConnection()) {
                con.start();

                Session ssn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Destination dtn = ssn.createTopic(ps.getProperty("TOPIC_FROM"));

                MessageConsumer mc = ssn.createConsumer(dtn);

                Message msg = mc.receive();

                String json_msg = null;

                if (msg instanceof TextMessage txt_msg) {
                    json_msg = txt_msg.getText();
                }

                Mensaje msjf = new Gson().fromJson(json_msg, Mensaje.class);
                
                // start operar
                Pedido objPedido = new Gson().fromJson(msjf.getContenido(), Pedido.class);
                
                Factura objFactura = new Factura();
                objFactura.setCodigoDeCliente(objPedido.getCodigoDeCliente());
                objFactura.setNombreDeCliente(objPedido.getNombreDeCliente());
                objFactura.setRucDeCliente(objPedido.getRucDeCliente());
                objFactura.setItems(objPedido.getItems());
                // end operar

                // start insercion en bbdd
                ConexionMariaDB db = new ConexionMariaDB();
                db.insert(objFactura);
                // end insercion en bbdd
                
                // start creando mensaje para modulo de cuentas x cobrar
                Mensaje msjt = new Mensaje();
                msjt.setEstado(0);
                msjt.setContenido(new Gson().toJson(msjt));

                // mandando mensaje al modulo de facturacion
                Productor prod = new Productor();
                prod.enviarMensajeCuentasPorCobrar(msjt);
            } catch (JMSException ex) {
                Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        this.consumirMensajeAdministracionDeInventarioYReserva();
    }
}


// https://www.codeusingjava.com/boot/active
// https://stackoverflow.com/questions/38636254/how-to-convert-json-to-java-object-using-gson
// https://simplesolution.dev/java-convert-json-file-to-java-object-using-gson/
