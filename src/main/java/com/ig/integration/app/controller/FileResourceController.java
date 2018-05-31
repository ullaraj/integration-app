package com.ig.integration.app.controller;


import com.ig.integration.app.exceptionhandling.BrokerConfigException;
import com.ig.integration.app.util.JsonUtil;
import com.ig.integration.app.domain.BrokerConfig;
import com.ig.integration.app.domain.Order;
import com.ig.integration.app.exceptionhandling.UploadException;
import com.ig.integration.app.messaging.MessageProducer;
import com.ig.integration.app.service.ConfigurationUpdateService;
import com.ig.integration.app.service.FileProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

/**
 * FileController that delegates to the fileprocessing service and
 * Message producer service.
 */
@RestController
public class FileResourceController implements  ResourceController{

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private FileProcessorService xmlFileProcessorService;

    @Autowired
    ConfigurationUpdateService producerConfigurationUpdateService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    BrokerConfig brokerConfig;

    @Autowired
    JsonUtil jsonUtil;

    @GetMapping("/upload")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("broker",brokerConfig);
            mav.setViewName("upload");
        return mav;
    }

    @PostMapping("file/upload")
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile fileName ,@ModelAttribute BrokerConfig brokerConfig) {
        log.debug(" The uploaded file name is   "+fileName.getOriginalFilename());

        validateFile(fileName);

        validateBrokerConfig(brokerConfig);

        List<Order> orders = xmlFileProcessorService.process(fileName);

        producerConfigurationUpdateService.updateBrokerDetails(brokerConfig);

        String jsonMessage = jsonUtil.convertToJsonMessage(orders);

        log.debug("JSON Text to send to the broker "+jsonMessage);

        messageProducer.send(brokerConfig.getDestination(), jsonMessage);

        return ResponseEntity.status(HttpStatus.OK).body(jsonMessage);
    }
    private void validateFile(MultipartFile file){
        if (file.isEmpty()) {
            throw new UploadException("Browse to upload an Order.xml file");

        }
    }

    private void validateBrokerConfig(BrokerConfig brokerConfig){
        if(brokerConfig.getBrokerUrl().isEmpty()
                || brokerConfig.getBrokerPassword().isEmpty()
                || brokerConfig.getBrokerUsername().isEmpty()
                || brokerConfig.getDestination().isEmpty()){
                throw  new BrokerConfigException("Please ensure all broker config parameters are updated.");
        }

    }



}
