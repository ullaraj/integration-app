package com.ig.integration.app.service;

import com.ig.integration.app.domain.BrokerConfig;
import com.ig.integration.app.messaging.DefaultMessageProducerConfiguration;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerConfigurationUpdateService implements ConfigurationUpdateService {

    @Autowired
    DefaultMessageProducerConfiguration defaultMessageProducerConfiguration;

    @Override
    public void updateBrokerDetails(BrokerConfig brokerConfig) {
        ActiveMQConnectionFactory activeMQConnectionFactory = defaultMessageProducerConfiguration.activeMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerConfig.getBrokerUrl());
        activeMQConnectionFactory.setUserName(brokerConfig.getBrokerUsername());
        activeMQConnectionFactory.setPassword(brokerConfig.getBrokerPassword());
    }
}
