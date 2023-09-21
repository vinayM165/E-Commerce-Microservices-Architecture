package com.vinayM.orderservice.Service;

import com.vinayM.orderservice.Model.*;
import com.vinayM.orderservice.Repository.OrderRepository;
import com.vinayM.orderservice.event.OrderPlacedEvent;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    OrderRepository repository;
    @Autowired
    private KafkaTemplate<String,OrderPlacedEvent> template;

    public static final Logger log = LoggerFactory.getLogger("RestTemplate.logs");
    public ResponseEntity placeOrder(OrderRequest orderRequest){
        try {
            List<OrderLineItems> orderList = orderRequest.getOrderLineItemsList();
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .orderLineItemsList(orderList)
                    .build();
            log.info(orderList.toString());
            orderList.forEach(e -> {
                String Url = "http://inventory-service/api/inventory/";
                ParameterizedTypeReference<List<InventoryDto>> typeRef = new ParameterizedTypeReference<List<InventoryDto>>() {
                };
                ResponseEntity<List<InventoryDto>> response = restTemplate.exchange(Url + e.getSkucode(), HttpMethod.GET, null, typeRef);
                log.info("Inventory Object at 0:" + Objects.requireNonNull(response.getBody()).get(0).getSkuCode());
            });
            orderList.forEach(e -> log.info(e.toString()));
            try {
                if (orderList.stream().allMatch(this::isInStock)) {
                    repository.save(order);
                    try {
                        template.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                        log.info("Message has been send to kafka successfully!!!!");
                    }catch (Exception e){
                        e.printStackTrace();
                        log.info("Something went wrong with kafka");
                    }
                    return new ResponseEntity("Order Placed SuccessFully!",HttpStatus.CREATED);
                }
            }catch (IndexOutOfBoundsException e){
                return ResponseEntity.badRequest().body("please share product skuCode which are present in inventory!");
            }
            return ResponseEntity.badRequest().body("Order Quantity is greater than Inventory stock");
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Items you have requested are not available in inventory!");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        try{
         return ResponseEntity.ok(repository.findAll().stream()
                 .map(this::mapToOrderResponse)
                 .collect(Collectors.toList()));
    }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public OrderResponse mapToOrderResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderLineItemsList(order.getOrderLineItemsList())
                .build();
    }

    public boolean isInStock(OrderLineItems item) throws IndexOutOfBoundsException{
            String Url = "http://inventory-service/api/inventory/";
            ParameterizedTypeReference<List<InventoryDto>> typeRef = new ParameterizedTypeReference<List<InventoryDto>>() {
            };
            ResponseEntity<List<InventoryDto>> response = restTemplate.exchange(Url + item.getSkucode(), HttpMethod.GET, null, typeRef);
            log.info(String.valueOf(response.getBody().get(0).getQuantity()));
            log.info("order quantity : " +item.getQuantity());
            return item.getQuantity()<Objects.requireNonNull(response.getBody()).get(0).getQuantity();
    }
}
