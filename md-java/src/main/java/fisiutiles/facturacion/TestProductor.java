package fisiutiles.facturacion;

import com.google.gson.Gson;
import java.util.ArrayList;
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


public class TestProductor {
    
    public static void main(String[] args) {
        Item a = new Item("desc 1", 5, 41.2);
        Item b = new Item("desc 2", 4, 13.7);
        Item c = new Item("desc 3", 3, 8.5);
        
        ArrayList<Item> items = new ArrayList<>();
        items.add(a);
        items.add(b);
        items.add(c);
        
        // Factura fac = new Factura(11, "19200038", "juan", "ruc495", items, 153.4, 15.6);
        Factura fac = new Factura(22, "14266484", "paco", "ruc511", items, 133.4, 25.6);
        
        Gson gson = new Gson();
        String str = gson.toJson(fac);
        
        new TestProductor().enviarMensajeFacturacion(str);
    }
    
    private Properties myProperties;
    
    public TestProductor() {
        this.myProperties = new PropertiesReader().getProperties();
    }
    
    public void enviarMensajeFacturacion(String mensaje) {
        try {
            ConnectionFactory myConnectionFactory = new ActiveMQConnectionFactory(myProperties.getProperty("URL"));
            Connection myConnection = myConnectionFactory.createConnection();
            
            myConnection.start();
            
            Session mySession = myConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            Destination myDestination = mySession.createTopic(myProperties.getProperty("TOPIC_FROM"));
            
            // Destination myDestination = mySession.createQueue(myProperties.getProperty("TOPIC"));
            
            MessageProducer myProducer = mySession.createProducer(myDestination);

            TextMessage myMessage = mySession.createTextMessage(mensaje);
            
            myProducer.send(myMessage);
            
            System.out.println("TEST-PRODUCTOR: " + myMessage.getText());
            
            myConnection.close();
        } catch (JMSException ex) {
            Logger.getLogger(TestProductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

// https://www.codeusingjava.com/boot/active