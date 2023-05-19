package org.apektas.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apektas.model.Location;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarLocationConsumer {
    // if let following than it tries to map String to location then fail
    // @KafkaListener(topics = "t-location", groupId = "cg-all-location")
    @KafkaListener(topics = "t-location", groupId = "cg-all-location",
            containerFactory = "allLocationContainerFactory",
            errorHandler = "locationErrorHandler")
    public void listenAllMessages(ConsumerRecord<String, Location> record){
        if (record.value().getDistance() % 3 == 0) {
            log.error("Sample exception on partition: {} distance: {}", record.partition(), record.value().getDistance());
            throw  new IllegalArgumentException("Sample exception");
        }

        log.info("listen-all: {}", record.value());
    }

    @KafkaListener(topics = "t-location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
    public void filteredMessages(Location location){
        log.info("listen-far: {}", location);
    }
}
