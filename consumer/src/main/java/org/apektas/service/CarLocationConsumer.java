package org.apektas.service;

import lombok.extern.slf4j.Slf4j;
import org.apektas.model.Location;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarLocationConsumer {
    // if let following than it tries to map String to location then fail
    // @KafkaListener(topics = "t-location", groupId = "cg-all-location")
    @KafkaListener(topics = "t-location", groupId = "cg-all-location", containerFactory = "allLocationContainerFactory")
    public void listenAllMessages(Location location){
        log.info("listen-all: {}", location);
    }

    @KafkaListener(topics = "t-location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
    public void filteredMessages(Location location){
        log.info("listen-far: {}", location);
    }
}
