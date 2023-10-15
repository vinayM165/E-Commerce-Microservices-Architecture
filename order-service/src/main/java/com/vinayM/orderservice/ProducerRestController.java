package com.vinayM.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProducerRestController {

	@Autowired
	OutBoundChannel gateway;

	@PostMapping("/publishMessage")
	public String publishMessage(@RequestBody String message) {
		gateway.sendMsgToPubSub(message);
		return "Message sent to pubsub successfully";
	}

}
