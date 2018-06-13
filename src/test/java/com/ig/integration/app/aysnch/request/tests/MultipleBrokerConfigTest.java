package com.ig.integration.app.aysnch.request.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.AbstractBaseTestSupport;
import com.ig.integration.app.domain.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultipleBrokerConfigTest extends AbstractBaseTestSupport {

    private List<String> inputList;

    private final List<Order> expectedOrders = new ArrayList<>();

    @Autowired
    ObjectMapper mapper;




}
