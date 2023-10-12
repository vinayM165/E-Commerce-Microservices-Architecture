package com.vinayM.productservice;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;

@RestController
public class PubSubDemoController {
	String message;

	@GetMapping("/getMessage")
	public String getMessage() {
		return "Message From GCP "+message;
	}

	@Bean
	public PubSubInboundChannelAdapter messageAdapter(@Qualifier("inputChannel") MessageChannel inputChannel,
			PubSubTemplate template) {
//		importi-topic-sub is  a subscription to the topic
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template, "ProductAddTopicSubscription");
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
	}

}
