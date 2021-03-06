package com.ig.integration.app.service;

import com.ig.integration.app.domain.Order;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

public interface FileProcessorService {


    default Stream<String> getFileStream(InputStream inputStream){
        return new BufferedReader(new InputStreamReader(inputStream)).lines();
    }

     List<Order> process(MultipartFile file);

     List<Order> getOrders(Stream<String> xmlFileStream);

}
