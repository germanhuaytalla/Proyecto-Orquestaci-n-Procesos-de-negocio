package fisiutiles.facturacion;

import com.google.gson.Gson;
import java.util.Properties;
import javax.jms.Session;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class Productor {

    private Properties ps;

    public Productor() {
        this.ps = new PropertiesReader().getProperties();
    }

    public void enviarMensajeCuentasPorCobrar(Mensaje msj) {
        String url = "tcp://" + ps.getProperty("ACTIVEMQ_HOST") + ":" + ps.getProperty("ACTIVEMQ_PORT");
        String topic_to = ps.getProperty("TOPIC_TO");
        ConnectionFactory cf = new ActiveMQConnectionFactory(url);

        try ( Connection con = cf.createConnection()) {
            con.start();

            Session ssn = con.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination dtn = ssn.createTopic(topic_to);

            MessageProducer mp = ssn.createProducer(dtn);

            String json_msj = new Gson().toJson(msj);

            TextMessage tm = ssn.createTextMessage(json_msj);

            mp.send(tm);
        } catch (JMSException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

// https://www.codeusingjava.com/boot/active
// https://mariadb.com/resources/blog/how-to-connect-java-applications-to-mariadb-using-jdbc/
