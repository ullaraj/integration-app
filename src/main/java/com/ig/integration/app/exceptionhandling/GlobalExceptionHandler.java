package com.ig.integration.app.exceptionhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handling different types of application exception's here
 */
@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    @Autowired
    ErrorResponse exceptionResponse ;

    private  static final String VIEW_NAME="uploadStatus";

    @ExceptionHandler(UploadException.class)
    public ModelAndView  fileUploadException(UploadException e, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("message", e);
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setDetails("Please select a file to upload");
        return createModelViewResponse(exceptionResponse);
    }

    @ExceptionHandler(BrokerConfigException.class)
    public ModelAndView  brokerConfigException(BrokerConfigException e, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("message", e);
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setDetails("Please ensure all broker config parameters are updated.");
        return createModelViewResponse(exceptionResponse);
    }
    @ExceptionHandler(FileProcessingException.class)
    public ModelAndView  brokerConfigException(FileProcessingException e, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("message", e);
        exceptionResponse.setMessage(e.getMessage());
        exceptionResponse.setDetails("Error occured while processing the file .Please upload a valid file.");

        return createModelViewResponse(exceptionResponse);
    }

     private ModelAndView createModelViewResponse(ErrorResponse exceptionResponse){
         ModelAndView modelAndView = new ModelAndView();
         modelAndView.addObject("exceptionResponse",exceptionResponse);
         modelAndView.setViewName("uploadStatus");
         modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
         return modelAndView;
     }

}
