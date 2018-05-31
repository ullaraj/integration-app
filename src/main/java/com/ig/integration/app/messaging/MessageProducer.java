package com.ig.integration.app.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class MessageProducer {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String message) {
        log.info("sending message='{}' to destination='{}'", message, destination);
        jmsTemplate.convertAndSend(destination, message);
    }
}
