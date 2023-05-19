package org.apektas.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apektas.model.Location;
import org.apektas.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConsumerConfig {


    // 2. Consume user objects from Kafka
    public ConsumerFactory<String, User> userConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // required
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-via-config");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(User.class));

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory());
        return factory;
    }


    // location consumer config
    public ConsumerFactory<String, Location> locationConsumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // required
        //props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-location");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(Location.class));

    }

    //allLocationContainerFactory

    @Bean(name="allLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Location> allLocationContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Location> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(locationConsumerFactory());

        // global error handler
        // advanced usage  can be found as follows
        // https://github.com/dilipsundarraj1/kafka-for-developers-using-spring-boot/blob/c617a03d377f72fa63f191be2517dee22099c141/library-events-consumer/src/main/java/com/learnkafka/config/LibraryEventsConsumerConfig.java
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    DefaultErrorHandler errorHandler() {
        return new DefaultErrorHandler((rec, thr) ->
                log.error("Exception occurred!!!!")

                /*
                log.error("Global Exception={}  i kafka-consumer record til topic={}, partition={}, offset={}, bestillingsId={} feilmelding={}",
                thr.getClass().getSimpleName(),
                rec.topic(),
                rec.partition(),
                rec.offset(),
                rec.key(),
                thr.getCause()
                )
                */

                ,
                new FixedBackOff(0, 0L)
                // if FixedBackOff(2000L, 2L) then we can see retry in the logs
        );
    }



    @Bean(name="farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Location> farLocationContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, Location> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(locationConsumerFactory());

        // enable filtering on messages
        factory.setRecordFilterStrategy(new RecordFilterStrategy<String, Location>() {
            @Override
            public boolean filter(ConsumerRecord<String, Location> consumerRecord) {
                // filter distance <= 100
                return consumerRecord.value().getDistance() <= 100;
            }
        });
        return factory;
    }

}
