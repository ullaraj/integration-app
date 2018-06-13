package com.ig.integration.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.controller.FileResourceController;
import com.ig.integration.app.domain.BrokerConfig;
import com.ig.integration.app.util.JsonUtil;
import org.junit.Assert;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationAppApplicationTests extends AbstractBaseTestSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @LocalServerPort
    private int port;

    @Autowired
    FileResourceController fileResourceController;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    JsonUtil jsonUtil;

    @Test
    public void contextLoads() {
        assertThat(fileResourceController).isNotNull();
    }

    @Test
    public void testValidFileUpLoad() throws Exception {
        MultiValueMap<String, Object> requestBodyMap = createMultiValuedMap("valid-orders.xml");
        setUpBrokerConfig(requestBodyMap);
        ResponseEntity<String> response = invokeMultipartFilePostRequest( requestBodyMap);
        String jsonResponse =jsonUtil.convertToJsonMessage(expectedOrders);

        Assert.assertEquals("The orders sent across and received in the response must be same ",jsonResponse,response.getBody());
    }

    private  ResponseEntity<String> invokeMultipartFilePostRequest(MultiValueMap<String, Object> requestBodyMap){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBodyMap, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        return restTemplate.exchange("http://localhost:"+port+"/file/upload",
                HttpMethod.POST, requestEntity, String.class);

    }

    @Test
    public void testInValidFileUload() throws Exception {

        MultiValueMap<String, Object> requestBodyMap = createMultiValuedMap("invalid-order-file.xml");
        setUpBrokerConfig(requestBodyMap);

        ResponseEntity<String> response =  invokeMultipartFilePostRequest( requestBodyMap);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }








}
