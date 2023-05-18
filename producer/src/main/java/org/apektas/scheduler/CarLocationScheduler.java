package org.apektas.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.apektas.model.Location;
import org.apektas.service.CarLocationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarLocationScheduler {

    @Autowired
    private CarLocationProducer carLocationProducer;

    private Location carOne;
    private Location carTwo;
    private Location carThree;

    public CarLocationScheduler(){
        var now = System.currentTimeMillis();
        carOne = new Location("car-one", now,0 );
        carTwo = new Location("car-two", now,110 );
        carThree = new Location("car-three", now,95 );
    }

    @Scheduled(fixedRate = 10000)
    public void generateCarLocation(){
        var now = System.currentTimeMillis();
        carOne.setTimestamp(now);
        carTwo.setTimestamp(now);
        carThree.setTimestamp(now);

        carOne.setDistance(carOne.getDistance() + 1);
        carTwo.setDistance(carTwo.getDistance() - 1);
        carThree.setDistance(carThree.getDistance() + 1);

        carLocationProducer.pushCarLocation(carOne);
        carLocationProducer.pushCarLocation(carTwo);
        carLocationProducer.pushCarLocation(carThree);

        log.info("Sent : {}", carOne);
        log.info("Sent : {}", carTwo);
        log.info("Sent : {}", carThree);
    }
}
