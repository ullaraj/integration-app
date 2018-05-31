package com.ig.integration.app.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class XmlJavaMapper {

    @Bean()
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ObjectMapper createObjectMapper(){
        ObjectMapper mapper = new XmlMapper();
        return  mapper;
    }

}
