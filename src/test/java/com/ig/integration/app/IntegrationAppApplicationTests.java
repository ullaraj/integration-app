package com.ig.integration.app;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.controller.FileResourceController;
import com.ig.integration.app.domain.BrokerConfig;
import com.ig.integration.app.domain.Order;
import com.ig.integration.app.exceptionhandling.FileProcessingException;
import com.ig.integration.app.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationAppApplicationTests {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @LocalServerPort
    private int port;

    @Autowired
    FileResourceController fileResourceController;

    List<String> inputList;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JsonUtil jsonUtil;


    private List<Order> expectedOrders = new ArrayList<>();

    @Test
    public void contextLoads() {
        assertThat(fileResourceController).isNotNull();
    }

    @Before
     public void testSetup() throws IOException {
        File f =  new ClassPathResource("valid-orders.xml").getFile();
        Stream<String> lines =Files.lines(f.toPath());
        inputList = lines.filter(x -> !x.startsWith("<?xml")).collect(Collectors.toList());
        inputList.forEach(x -> {
            try {
                expectedOrders.add(mapper.readValue(x, Order.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.debug("List of order Objects Expected "+expectedOrders);

    }

    @Test
    public void testValidFileUload() throws Exception {
        MultiValueMap<String, Object> bodyMap = createMultiValuedMap("valid-orders.xml");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/file/upload",
                HttpMethod.POST, requestEntity, String.class);

        String jsonResponse =jsonUtil.convertToJsonMessage(expectedOrders);

        Assert.assertEquals("The orders sent across and received in the response must be same ",jsonResponse,response.getBody());
    }

    @Test
    public void testInValidFileUload() throws Exception {
        MultiValueMap<String, Object> bodyMap = createMultiValuedMap("invalid-order-file.xml");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/file/upload",
                HttpMethod.POST, requestEntity, String.class);

            response.getBody();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    private MultiValueMap<String, Object>  createMultiValuedMap(String fileName) throws IOException {
        MultiValueMap<String, Object> multiValuedMap = new LinkedMultiValueMap<>();
        multiValuedMap.add("file", getUserFileResourceReal(fileName));
        BrokerConfig brokerConfig  = createBrokerConfig();

        multiValuedMap.add("brokerUrl",brokerConfig.getBrokerUrl());
        multiValuedMap.add("brokerUsername",brokerConfig.getBrokerUsername());
        multiValuedMap.add("brokerPassword",brokerConfig.getBrokerPassword());
        multiValuedMap.add("destination",brokerConfig.getDestination());
        return multiValuedMap;
    }

    private BrokerConfig createBrokerConfig(){
        //TODO Remove hard coded values;
        BrokerConfig brokerConfig = new BrokerConfig();
        brokerConfig.setBrokerUrl("vm://localhost?broker.persistent=false");
        brokerConfig.setBrokerPassword("admin");
        brokerConfig.setBrokerUsername("admin");
        brokerConfig.setDestination("helloworld.q");
        return brokerConfig;
    }

    private static Resource getUserFileResourceReal(String fileName) throws IOException {
        // Loading in memory to send Multipart File
        File f =  new ClassPathResource(fileName).getFile();
        Stream<String> lines =Files.lines(f.toPath());
        Path tempFile = Files.createTempFile("upload-test-file", ".xml");
        Files.write(tempFile, (Iterable<String>)lines::iterator);
        return new FileSystemResource(tempFile.toFile());

    }



}
