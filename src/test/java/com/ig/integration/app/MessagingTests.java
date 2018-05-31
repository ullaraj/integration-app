package com.ig.integration.app;

import com.ig.integration.app.domain.BrokerConfig;
import com.ig.integration.app.messaging.MessageConsumer;
import com.ig.integration.app.messaging.MessageProducer;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessagingTests {

        private static ApplicationContext applicationContext;

        @Autowired
        void setContext(ApplicationContext applicationContext) {
            MessagingTests.applicationContext = applicationContext;
        }

        @ClassRule
        public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

        @Autowired
        private MessageProducer sender;

        @Autowired
        private MessageConsumer receiver;
		
		@Autowired
        private BrokerConfig brokerConfig;

        @Test
        public void testReceive() throws Exception {
            sender.send(brokerConfig.getDestination(), "Hello Spring JMS ActiveMQ!");

            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
            assertThat(receiver.getLatch().getCount()).isEqualTo(0);
        }

    public static Resource getUserFileResource() throws IOException {
        //todo replace tempFile with a real file
        Path tempFile = Files.createTempFile("upload-test-file", ".txt");
        Files.write(tempFile, "some test content...\nline1\nline2".getBytes());
        System.out.println("uploading: " + tempFile);
        File file = tempFile.toFile();
        //to upload in-memory bytes use ByteArrayResource instead
        return new FileSystemResource(file);
    }

}
