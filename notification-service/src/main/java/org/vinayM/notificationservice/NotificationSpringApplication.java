package org.vinayM.notificationservice;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class NotificationSpringApplication {
    String message;
    private static final Logger log = LoggerFactory.getLogger("notification_log");
    public static void main(String[] args) {
        SpringApplication.run(NotificationSpringApplication.class);
    }


//    @KafkaListener(topics = "notificationTopic",groupId = "notification-group")
//    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
//        log.info("Received notification for order :" + orderPlacedEvent.orderNumber);
//    }
    @GetMapping("/getMessage")
    public String getMessage() {
        return "Message From GCP "+message;
    }

    @Bean
    public PubSubInboundChannelAdapter messageAdapter(@Qualifier("inputChannel") MessageChannel inputChannel,
                                                      PubSubTemplate template) {
//		importi-topic-sub is  a subscription to the topic
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template, "OrderPlacedSubscription");
        adapter.setOutputChannel(inputChannel);
        return adapter;
    }

    @Bean
    MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "inputChannel")
    public void receiveMessage(String payload) {
        this.message = payload;
        log.info("Received message from pub-sub as : " + payload);
    }

}