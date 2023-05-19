package org.apektas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apektas.model.Location;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class CarLocationProducer {

    private final KafkaTemplate<String, Location> locationKafkaTemplate;


    public void pushCarLocation(Location location){

        //CompletableFuture<>
        var future = locationKafkaTemplate.send("t-location", location);
        future.whenComplete((result, ex) -> {
            if (ex != null){
                log.error("Exception occurred!!!! exception: {}", ex.getMessage());
            }else {
                log.info("Message send: {}, partition: {}", result.getProducerRecord().value(), result.getRecordMetadata().partition());
            }
        });


    }
}
