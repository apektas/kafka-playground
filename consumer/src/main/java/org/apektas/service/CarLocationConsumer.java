package org.apektas.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apektas.model.Location;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarLocationConsumer {
    // if let following than it tries to map String to location then fail
    // @KafkaListener(topics = "t-location", groupId = "cg-all-location")
    // Multiple topics to listen
    // @KafkaListener(id = "multiGroup", topics = { "foos", "bars" })
    @KafkaListener(topics = { "t-location" }, groupId = "cg-all-location",
            containerFactory = "allLocationContainerFactory"
            //errorHandler = "locationErrorHandler"
            )
    @RetryableTopic(
            autoCreateTopics = "true", attempts = "3",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            backoff = @Backoff(delay = 300, maxDelay = 1000, multiplier = 1.5, random = true),
            dltTopicSuffix = "-dead"
    )
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
