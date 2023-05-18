package org.apektas.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apektas.model.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerService {

    @KafkaListener(topics = "t-hello")
    public void consume(String msg){
        log.info(msg);
    }

    @KafkaListener(topics = "${user.topic.name}",
            groupId = "${user.topic.group.id}",
            containerFactory = "userKafkaListenerContainerFactory")
    public void consume(User user) {
        log.info(String.format("User created -> %s", user));
    }
}
