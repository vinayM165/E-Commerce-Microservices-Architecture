package org.vinayM.notificationservice;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class NotificationSpringApplication {
    private static final Logger log = LoggerFactory.getLogger("notification_log");
    public static void main(String[] args) {
        SpringApplication.run(NotificationSpringApplication.class);
    }


    @KafkaListener(topics = "notificationTopic",groupId = "notification-group")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("Received notification for order :" + orderPlacedEvent.orderNumber);
    }
}