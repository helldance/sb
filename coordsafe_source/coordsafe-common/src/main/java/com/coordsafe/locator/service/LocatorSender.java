package com.coordsafe.locator.service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import com.coordsafe.locator.entity.Locator;

public class LocatorSender {
/*    @Autowired
    private JmsTemplate jmsTemplate;
*/   
    public void sendOrder(final Locator locator){
        //jmsTemplate.send((MessageCreator) locator);
    }

}
