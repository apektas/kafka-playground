package org.apektas;


import org.apektas.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication{ //  implements CommandLineRunner {

    //@Autowired
    //ProducerService producerService;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    //@Override
    //public void run(String... args) throws Exception {
    //    producerService.sendMessage("Testing " + ThreadLocalRandom.current().nextInt());
    //}
}
