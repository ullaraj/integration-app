package com.ig.integration.app.controller;

import com.ig.integration.app.domain.BrokerConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceController {

    ResponseEntity<String> save(@RequestParam("file") MultipartFile file,@ModelAttribute BrokerConfig brokerConfig) ;
}
