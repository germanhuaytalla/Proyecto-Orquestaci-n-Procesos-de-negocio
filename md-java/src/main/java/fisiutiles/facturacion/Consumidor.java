package fisiutiles.facturacion;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import com.google.gson.Gson;

public class Consumidor {

    public static void main(String[] args) {
        new Consumidor().consumirMensajeAdministracionDeInventarioYReserva();
    }

    private Properties ps;

    public Consumidor() {
        this.ps = new PropertiesReader().getProperties();
    }

    private void consumirMensajeAdministracionDeInventarioYReserva() {
        String url = "tcp://" + ps.getProperty("ACTIVEMQ_HOST") + ":" + ps.getProperty("ACTIVEMQ_PORT");
        String topic_from = ps.getProperty("TOPIC_FROM");
        ConnectionFactory cf = new ActiveMQConnectionFactory(url);

        while (true) {
            try ( Connection con = cf.createConnection()) {
                con.start();

                Session ssn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Destination dtn = ssn.createQueue(topic_from);

                MessageConsumer mc = ssn.createConsumer(dtn);

                Message msg = mc.receive();

                String json_msg = null;

                if (msg instanceof BytesMessage) {
                    BytesMessage bytesMessage = (BytesMessage) msg;
                    int length = (int) bytesMessage.getBodyLength();
                    byte[] body = new byte[length];
                    int readBytes = bytesMessage.readBytes(body);
                    String text = new String(body, StandardCharsets.UTF_8);
                    json_msg = text;
                }
                
                Mensaje msjf = new Gson().fromJson(json_msg, Mensaje.class);
                
                // start operar
                Orden objPedido = new Gson().fromJson(msjf.getContenido().toString(), Orden.class);
                ArrayList<ItemCalculado> items = new ArrayList<>();
                
                for (Item it : objPedido.getItems()) {
                    ItemCalculado itc = new ItemCalculado();
                    itc.setCodigo(it.getCodigo());
                    itc.setDescripcion(it.getDescripcion());
                    itc.setCantidad(it.getCantidad());
                    itc.setPrecioUnitario(it.getPrecioUnitario());
                    items.add(itc);
                }

                Factura objFactura = new Factura();
                objFactura.setCodigoDeCliente(objPedido.getCodigoDeCliente());
                objFactura.setNombreDeCliente(objPedido.getNombreDeCliente());
                objFactura.setRucDeCliente(objPedido.getRucDeCliente());
                objFactura.setItems(items);
                // end operar

                // start insercion en bbdd
                //ConexionMariaDB db = new ConexionMariaDB();
                //db.insert(objFactura);
                // end insercion en bbdd

                // start creando mensaje para modulo de cuentas x cobrar

                Mensaje msjt = new Mensaje();
                
                msjt.setEstado(0);
                msjt.setContenido(new Gson().toJsonTree(objFactura).getAsJsonObject());
                System.out.println(msjt);
                // end creando mensaje para modulo de cuentas x cobrar

                // start mandando mensaje al modulo de cuentas x cobrar
                Productor prod = new Productor();
                prod.enviarMensajeCuentasPorCobrar(msjt);
                // end mandando mensaje al modulo de cuentas x cobrar
            } catch (JMSException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }
}

// https://www.codeusingjava.com/boot/active
// https://stackoverflow.com/questions/38636254/how-to-convert-json-to-java-object-using-gson
// https://simplesolution.dev/java-convert-json-file-to-java-object-using-gson/
