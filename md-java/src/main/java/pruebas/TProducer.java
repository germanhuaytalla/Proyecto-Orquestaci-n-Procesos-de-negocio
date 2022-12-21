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
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQConnection;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 *
 * @author juanp
 */
public class TProducer {
    
    private static String subject = "EJEMPLEX_queue"; 
    // private static String subject = "EJEMPLEX_topic";
    
    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection myConnection = connectionFactory.createConnection();
            myConnection.start();
            Session mySession = myConnection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = mySession.createQueue(subject);
            // Destination destination = mySession.createTopic(subject);
            MessageProducer producer = mySession.createProducer(destination);
            
            TextMessage myMessage = mySession
                    .createTextMessage("EJEMPLO   DE ---TOPICO--1--.");
            producer.send(myMessage);
            
            System.out.println("JCG printing@@ '" + myMessage.getText() + "'");
            myConnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(TProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
