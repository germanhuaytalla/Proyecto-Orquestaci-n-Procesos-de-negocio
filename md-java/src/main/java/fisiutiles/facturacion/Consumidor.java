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

public class Consumidor implements Runnable {

    private Properties myProperties;

    public Consumidor() {
        this.myProperties = new PropertiesReader().getProperties();
    }

    private void consumirMensajeAdministracionDeInventarioYReserva() {
        while (true) {
            ConnectionFactory myConnectionFactory = new ActiveMQConnectionFactory(myProperties.getProperty("URL"));

            try (Connection myConnection = myConnectionFactory.createConnection()) {
                myConnection.start();

                Session mySession = myConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Destination myDestination = mySession.createTopic(myProperties.getProperty("TOPIC_FROM"));

                MessageConsumer myConsumer = mySession.createConsumer(myDestination);

                Message myMessage = myConsumer.receive();

                if (myMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) myMessage;
                    System.out.println("CONSUMIDOR: " + textMessage.getText());
                }
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
