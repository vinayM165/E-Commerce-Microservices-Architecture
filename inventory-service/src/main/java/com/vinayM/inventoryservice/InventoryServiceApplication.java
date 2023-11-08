package com.vinayM.inventoryservice;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.vinayM.inventoryservice.Event.ProductAddEvent;
import com.vinayM.inventoryservice.Exception.DuplicateInventoryException;
import com.vinayM.inventoryservice.Model.Inventory;
import com.vinayM.inventoryservice.Repository.InventoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import java.math.BigInteger;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {
	private Logger log = LoggerFactory.getLogger("InventoryLogs");

	@Autowired
	InventoryRepo repo;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepo repo){
//		return args -> {
//			repo.save(new Inventory(454L,"iphone13",122));
//			repo.save(new Inventory(232L,"samsungs23",1221));
//			repo.save(new Inventory(554L,"redmi13",3232));
//		};
//	}

//	@KafkaListener(topics = "ProductAddTopic",groupId = "productGroup")
//	public void ProdListener(ProductAddEvent addEvent){
//		log.info("Kafka Received from Product add topic!!!");
//	}

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
	public void receiveMessage(String payload) throws DuplicateInventoryException {
		ProductAddEvent event = null;
		try {
			event = JsonConverter.convertJsonToObject(payload, ProductAddEvent.class);
			log.info("Received Event : " + event.toString());
			Inventory i = Inventory.builder()
					.skuCode(event.getProduct_name())
					.quantity(event.getProd_quantity())
					.id((event.getProduct_id()))
					.build();
			if (repo.findById(i.getId()).isPresent())
				throw new DuplicateInventoryException("Recived Event that tried to add duplicated data!!!");
			repo.save(i);
			log.info("Inventory has been added successfully!!!");
		} catch (NumberFormatException e) {
			assert event != null;
			log.info(String.valueOf(new BigInteger(event.getProduct_id(), 16)) + " is not a valid integer.");
			e.printStackTrace();
		} catch (Exception e) {
			if (!(e instanceof DuplicateInventoryException)) {
				log.info(String.valueOf(new BigInteger(event.getProduct_id(), 16)) + " is not a valid integer.");
				e.printStackTrace();
			}
		}
	}
}
