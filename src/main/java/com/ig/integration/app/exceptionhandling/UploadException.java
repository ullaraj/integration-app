package com.ig.integration.app.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UploadException extends RuntimeException{

     public UploadException(String message){
        super(message);
     }

}
