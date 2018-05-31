package com.ig.integration.app.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BrokerConfigException  extends RuntimeException {

    public BrokerConfigException(String message){
        super(message);
    }
}
