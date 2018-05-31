package com.ig.integration.app.service;

import com.ig.integration.app.domain.BrokerConfig;

public interface ConfigurationUpdateService {

    void updateBrokerDetails(BrokerConfig brokerConfig);
}
