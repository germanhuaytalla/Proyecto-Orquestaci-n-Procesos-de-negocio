package fisiutiles.facturacion;

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
    
    public static void main(String[] args) {
        new Productor().enviarMensajeCuentasPorCobrar("mensajito");
    }
    
    private Properties myProperties;
    
    public Productor() {
        this.myProperties = new PropertiesReader().getProperties();
    }
    
    public void enviarMensajeCuentasPorCobrar(String mensaje) {
        try {
            ConnectionFactory myConnectionFactory = new ActiveMQConnectionFactory(myProperties.getProperty("URL"));
            Connection myConnection = myConnectionFactory.createConnection();
            
            myConnection.start();
            
            Session mySession = myConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Destination myDestination = mySession.createTopic(myProperties.getProperty("TOPIC_TO"));
            
            MessageProducer myProducer = mySession.createProducer(myDestination);

            TextMessage myMessage = mySession.createTextMessage(mensaje);
            
            myProducer.send(myMessage);
            
            System.out.println("PRODUCTOR: " + myMessage.getText());
            
            myConnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

// https://www.codeusingjava.com/boot/active
// https://mariadb.com/resources/blog/how-to-connect-java-applications-to-mariadb-using-jdbc/