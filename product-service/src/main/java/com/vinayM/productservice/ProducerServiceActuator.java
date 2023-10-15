package com.vinayM.productservice;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;

@Component
public class ProducerServiceActuator {

	@Bean
	@ServiceActivator(inputChannel = "outboundMsgChanel")
	public PubSubMessageHandler messageSender(PubSubTemplate pubSubTemplate) {
//		importi-topic is pubsub producer topic
		return new PubSubMessageHandler(pubSubTemplate, "ProductAddTopic");
	}

}
