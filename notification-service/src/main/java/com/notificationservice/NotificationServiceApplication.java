package com.notificationservice;

import io.micrometer.tracing.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.*;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor


public class NotificationServiceApplication {
    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
        System.out.println("Service running..");
    }

    @KafkaListener(topics = "notificationTopics")
//    public void  hanldeNotification(OrderPlacedEvent orderPlaceEvent){
//        log.info("Received Notification {}" ,orderPlaceEvent.getOrderNUmber());
//    }
    public void handleNotification(OrderPlacedEvent orderPlaceEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderPlaceEvent);
            log.info("TraceId- {}, Received Notification for Order - {}", this.tracer.currentSpan().context().traceId(),
                    orderPlaceEvent.getOrderNUmber());
        });

    }
}
