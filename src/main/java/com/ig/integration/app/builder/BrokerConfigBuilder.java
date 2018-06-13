package com.ig.integration.app.builder;

import com.ig.integration.app.domain.BrokerConfig;

public class BrokerConfigBuilder {

    private String brokerUrl;
    private String brokerUsername;
    private String brokerPassword;
    private String destination;


    public BrokerConfigBuilder setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
        return this;
    }

    public BrokerConfigBuilder setBrokerUsername(String brokerUsername) {
        this.brokerUsername = brokerUsername;
        return this;
    }

    public BrokerConfigBuilder setBrokerPassword(String brokerPassword) {

        this.brokerPassword = brokerPassword;
        return this;
    }

    public BrokerConfigBuilder setDestination(String destination) {

        this.destination = destination;
        return this;
    }

    public BrokerConfig build(){
        return new BrokerConfig(brokerUrl,brokerUsername,brokerPassword,destination);
    }



}
