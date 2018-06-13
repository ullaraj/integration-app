package com.ig.integration.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.domain.Order;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractTestSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    protected List<String> inputList;

    protected final List<Order> expectedOrders = new ArrayList<>();

    @Autowired
    ObjectMapper mapper;


    @Before
    public void testSetup() throws IOException {
        File f = new ClassPathResource("valid-orders.xml").getFile();
        Stream<String> lines = Files.lines(f.toPath());
        inputList = lines.filter(x -> !x.startsWith("<?xml")).collect(Collectors.toList());
        inputList.forEach(x -> {
            try {
                expectedOrders.add(mapper.readValue(x, Order.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.debug("List of order Objects Expected " + expectedOrders);
    }
}
