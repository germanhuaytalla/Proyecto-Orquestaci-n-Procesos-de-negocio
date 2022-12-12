package fisiutiles.facturacion;

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

public class Consumidor/* implements Runnable*/ {
    
    public static void main(String[] args) {
        new Consumidor().consumirMensaje();
    }

    private Properties myProperties;
    
    public Consumidor() {
        this.myProperties = new PropertiesReader().getProperties();
    }
    
    public void consumirMensaje() {
        try {
            ConnectionFactory myConnectionFactory = new ActiveMQConnectionFactory(myProperties.getProperty("URL"));
            Connection myConnection = myConnectionFactory.createConnection();
            
            myConnection.start();
            
            Session mySession = myConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Destination myDestination = mySession.createQueue(myProperties.getProperty("SUBJECT"));
            
            MessageConsumer myReceiver = mySession.createConsumer(myDestination);
            
            Message myMessage = myReceiver.receive();
            
            if (myMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) myMessage;
                System.out.println("Receiver message: " + textMessage.getText());
            }
            
            myConnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
