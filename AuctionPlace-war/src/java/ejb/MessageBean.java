/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Daniel Losvik
 */
@JMSDestinationDefinition(
        name = "java:app/BidWinnerTopic", 
        interfaceName = "javax.jms.Topic", 
        resourceAdapter = "jmsra", 
        destinationName = "BidWinnerTopic")

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(
            propertyName = "clientId", 
            propertyValue = "java:app/BidWinnerTopic"),
    @ActivationConfigProperty(
            propertyName = "destinationLookup", 
            propertyValue = "java:app/BidWinnerTopic"),
    @ActivationConfigProperty(
            propertyName = "subscriptionDurability", 
            propertyValue = "Durable"),
    @ActivationConfigProperty(
            propertyName = "subscriptionName", 
            propertyValue = "java:app/BidWinnerTopic"),
    @ActivationConfigProperty(
            propertyName = "destinationType", 
            propertyValue = "javax.jms.Topic")
})

public class MessageBean implements MessageListener {
    
    public MessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message.getBody(String.class));
        }
        catch (JMSException e) {
            System.out.print(e.toString());
        }
    }
    
}
