package org.apektas.service;

import lombok.AllArgsConstructor;
import org.apektas.model.Location;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class CarLocationProducer {

    private final KafkaTemplate<String, Location> locationKafkaTemplate;


    public void pushCarLocation(Location location){

        //CompletableFuture<>
        locationKafkaTemplate.send("t-location", location);

    }
}
