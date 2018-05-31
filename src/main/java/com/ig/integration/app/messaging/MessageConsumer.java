package com.ig.integration.app.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import java.util.concurrent.CountDownLatch;

public class    MessageConsumer {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${spring.activemq.destination}")
    public void receive(String message) {
        log.info("received message='{}'", message);
        latch.countDown();
    }
}
