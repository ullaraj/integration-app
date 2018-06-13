package com.ig.integration.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.builder.BrokerConfigBuilder;
import com.ig.integration.app.domain.BrokerConfig;
import com.ig.integration.app.domain.Order;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractBaseTestSupport {

    private final static Logger log = LoggerFactory.getLogger(AbstractBaseTestSupport.class.getName());

    protected static final List<String> inputList;

    protected static final List<Order> expectedOrders = new ArrayList<>();

    @Autowired
    ObjectMapper mapper;
    static{
        File f = null;
        try {
            f = new ClassPathResource("valid-orders.xml").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stream<String> lines = null;
        try {
            lines = Files.lines(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputList = lines.filter(x -> !x.startsWith("<?xml")).collect(Collectors.toList());


    }


    @Before
    public void testSetup() throws IOException {
        inputList.forEach(x -> {
            try {
                expectedOrders.add(mapper.readValue(x, Order.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.debug("List of order Objects Expected " + expectedOrders);
    }

    protected MultiValueMap<String, Object> createMultiValuedMap(String fileName) throws IOException {
        MultiValueMap<String, Object> multiValuedMap = new LinkedMultiValueMap<>();
        multiValuedMap.add("file", getUserFileResourceReal(fileName));
        return multiValuedMap;
    }

    private static Resource getUserFileResourceReal(String fileName) throws IOException {
        // Loading in memory to send Multipart File
        File f =  new ClassPathResource(fileName).getFile();
        Stream<String> lines =Files.lines(f.toPath());
        Path tempFile = Files.createTempFile("upload-test-file", ".xml");
        Files.write(tempFile, (Iterable<String>)lines::iterator);
        return new FileSystemResource(tempFile.toFile());

    }

    protected MultiValueMap<String, Object>  setUpBrokerConfig(MultiValueMap<String, Object> multiValuedMap) throws IOException {
        BrokerConfig brokerConfig  = createBrokerConfig("vm://localhost?broker.persistent=false","admin","admin","helloworld.q");
        multiValuedMap.add("brokerUrl",brokerConfig.getBrokerUrl());
        multiValuedMap.add("brokerUsername",brokerConfig.getBrokerUsername());
        multiValuedMap.add("brokerPassword",brokerConfig.getBrokerPassword());
        multiValuedMap.add("destination",brokerConfig.getDestination());
        return multiValuedMap;
    }

   /* private BrokerConfig createBrokerConfig(){
        //TODO Remove hard coded values;
        BrokerConfig brokerConfig = new BrokerConfig();
        brokerConfig.setBrokerUrl("vm://localhost?broker.persistent=false");
        brokerConfig.setBrokerPassword("admin");
        brokerConfig.setBrokerUsername("admin");
        brokerConfig.setDestination("helloworld.q");
        return brokerConfig;
    }*/

    private BrokerConfig createBrokerConfig(String brokerUrl,String brokerUserName,String brokerPassword, String destination){

        BrokerConfig brokerConfig = new BrokerConfigBuilder()
                        .setBrokerUrl(brokerUrl)
                        .setBrokerUsername(brokerUserName)
                        .setBrokerPassword(brokerPassword)
                        .setDestination(destination).build();
        return brokerConfig;
    }

}
