package com.vinayM.inventoryservice;

import com.vinayM.inventoryservice.Event.ProductAddEvent;
import com.vinayM.inventoryservice.Model.Inventory;
import com.vinayM.inventoryservice.Repository.InventoryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient

public class InventoryServiceApplication {
	private Logger log = LoggerFactory.getLogger("InventoryLogs");
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(InventoryRepo repo){
		return args -> {
			repo.save(new Inventory(454L,"iphone13",122));
			repo.save(new Inventory(232L,"samsungs23",1221));
			repo.save(new Inventory(554L,"redmi13",3232));
		};
	}

	@KafkaListener(topics = "ProductAddTopic",groupId = "productGroup")
	public void ProdListener(ProductAddEvent addEvent){
		log.info("Kafka Received from Product add topic!!!");
	}

}
