package org.apektas.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaKeyConsumer {

    @KafkaListener(topics = "t-hello")
    public void consume(ConsumerRecord<String, String> record){
        log.info("Key: {}, Partition: {}, Message: {}", record.key(), record.partition(), record.value());
    }
}
