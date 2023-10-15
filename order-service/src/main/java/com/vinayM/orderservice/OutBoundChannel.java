package com.vinayM.orderservice;


import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "myPubSubGateway", defaultRequestChannel = "outboundMsgChanel")
public interface OutBoundChannel {

	void sendMsgToPubSub(String msg);


}
