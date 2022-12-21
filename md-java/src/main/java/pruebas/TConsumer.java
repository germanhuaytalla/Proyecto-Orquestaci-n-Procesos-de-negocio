/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQConnection;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
/**
 *
 * @author juanp
 */
public class TConsumer {
    
    private static String subject = "EJEMPLEX_queue"; 
    // private static String subject = "EJEMPLEX_topic";
    
    public static void main(String[] args) {
        while (true) {
            
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection myConnection = connectionFactory.createConnection();
            myConnection.start();
            
            Session session = myConnection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            
            
            Destination myDestination = session.createQueue(subject);
            // Destination myDestination = session.createTopic(subject);
            
            MessageConsumer sendToConsumer = session.createConsumer(myDestination);
            
            Message myMessage = sendToConsumer.receive();
            
            if (myMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) myMessage;
                System.out.println("Received messageCOSUMER1 '" + textMessage.getText() + "'");
            }
            myConnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(TConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
