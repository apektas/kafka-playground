package org.apektas.controller;

import lombok.AllArgsConstructor;
import org.apektas.model.Location;
import org.apektas.model.User;
import org.apektas.service.CarLocationProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LocationController {

    CarLocationProducer carLocationProducer;

    @GetMapping("/location/{location}")
    public String postMessage(@PathVariable("location") final String location){
        carLocationProducer.pushCarLocation(Location.builder().carId("carId-1").timestamp(100l).distance(80).build());
        return "Message pushed!";
    }

}
