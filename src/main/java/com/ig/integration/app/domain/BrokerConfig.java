package com.ig.integration.app.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The default Broker configurations are loaded form application.properties.
 */
@Configuration
 public class BrokerConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String brokerUsername;

    @Value("${spring.activemq.password}")
    private String brokerPassword;

    @Value("${spring.activemq.destination}")
    private String destination;

    public BrokerConfig(){
    }

    public BrokerConfig(String brokerUrl,String brokerUsername,String brokerPassword,String destination){
        this.brokerUrl= brokerUrl;
        this.brokerUsername =brokerUsername;
        this.brokerPassword = brokerPassword;
        this.destination = destination;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public void setBrokerUsername(String brokerUsername) {
        this.brokerUsername = brokerUsername;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public void setBrokerPassword(String brokerPassword) {
        this.brokerPassword = brokerPassword;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Bean
    public BrokerConfig createBrokerConfig(){
        return new BrokerConfig(brokerUrl,brokerUsername,brokerPassword,destination);
    }
}
