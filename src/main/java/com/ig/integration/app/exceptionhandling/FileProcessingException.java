package com.ig.integration.app.exceptionhandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileProcessingException extends RuntimeException {

    public FileProcessingException(String message){
        super(message);
    }
}
