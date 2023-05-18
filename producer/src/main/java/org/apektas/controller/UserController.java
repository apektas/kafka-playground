package org.apektas.controller;

import lombok.AllArgsConstructor;
import org.apektas.model.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    KafkaTemplate<String, User> kafkaTemplate;

    @GetMapping("/user/{name}")
    public String postMessage(@PathVariable("name") final String name){
        kafkaTemplate.send("user-topic",
                name,
                User.builder()
                    .salary(1000l)
                    .name(name)
                    .department("testing")
                    .build()
    );
        return "Message pushed";
    }

}
