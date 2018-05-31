package com.ig.integration.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.domain.Order;
import com.ig.integration.app.exceptionhandling.FileProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class XmlFileProcessorService implements  FileProcessorService{

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Order>  process(MultipartFile file) {
        log.debug("Processing uploaded file");
        Stream<String> xmlFileContentStream=  getFileStream(getInputStream(file));
        List<Order> orders =getOrders(xmlFileContentStream);
        log.debug("Orders to be sent across "+orders);
        return orders;
    }

    //Skipping the xml tag
    @Override
    public List<Order> getOrders(Stream<String> xmlFileContentStream) {
        return xmlFileContentStream
                .filter(x -> !x.startsWith("<?xml"))
                .map(x -> {
                         Order order = null;
                           try {
                                order = objectMapper.readValue(x, Order.class);
                           } catch (Exception e) {
                                logException(e);
                           }
                         return order;
                }).collect(Collectors.toList());

    }

    private InputStream getInputStream(MultipartFile file){
        InputStream inputStream = null;
        try {
            inputStream =  file.getInputStream();
        } catch (Exception e) {
            logException(e);
        }
        return inputStream;
    }

    private void logException(Exception ex){
        log.error("An Exception occured while parsing the uploaded file "+ex);
        ex.printStackTrace();
        throw  new FileProcessingException("Error occured while processing the file .Please upload a valid file");
    }
}
