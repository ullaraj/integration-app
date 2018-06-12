package com.ig.integration.app.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.Future;

@Service
public class AsyncServices {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Async
    public Future<String> process(HttpEntity<MultiValueMap<String, Object>> requestEntity,int port) throws InterruptedException {
        log.info("###Start Processing with Thread id: " + Thread.currentThread().getId());

        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.exchange("http://localhost:"+port+"/file/upload",
                HttpMethod.POST, requestEntity, String.class);

        Thread.sleep(3000);

        String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
        return new AsyncResult<>(processInfo);
    }
}
