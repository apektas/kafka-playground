package org.apektas.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service
//@AllArgsConstructor
public class ProducerService {
    //private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) throws InterruptedException {
        for(int i=0; i < 100; i++){
            //kafkaTemplate.send("t-hello", "Hello " + msg);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
