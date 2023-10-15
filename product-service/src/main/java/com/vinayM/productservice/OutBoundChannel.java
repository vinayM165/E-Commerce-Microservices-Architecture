package com.vinayM.productservice;

import com.vinayM.productservice.Event.ProductAddEvent;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "myPubSubGateway", defaultRequestChannel = "outboundMsgChanel")
public interface OutBoundChannel {

	void sendMsgToPubSub(String msg);
	void sendProductEvent(ProductAddEvent productAddEvent);

}
