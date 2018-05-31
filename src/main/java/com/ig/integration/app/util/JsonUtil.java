package com.ig.integration.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ig.integration.app.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonUtil {


    public  String convertToJsonMessage(List<Order> orders){
        StringBuffer sb = new StringBuffer();
        ObjectMapper objectMapper = new ObjectMapper();
        orders.forEach(x-> {
            try {
                sb.append(objectMapper.writeValueAsString(x));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
          return  sb.toString();
    }
}
