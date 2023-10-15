package com.vinayM.orderservice;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class ProducerServiceActuator {

	@Bean
	@ServiceActivator(inputChannel = "outboundMsgChanel")
	public PubSubMessageHandler messageSender(PubSubTemplate pubSubTemplate) {
//		importi-topic is pubsub producer topic
		return new PubSubMessageHandler(pubSubTemplate, "OrderPlacesTopic");
	}

}
