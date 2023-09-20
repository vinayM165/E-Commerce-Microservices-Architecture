package com.vinayM.orderservice.Controller;

import com.vinayM.orderservice.Model.OrderRequest;
import com.vinayM.orderservice.Model.OrderResponse;
import com.vinayM.orderservice.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController()
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService service;
    @PostMapping()
    @CircuitBreaker(name = "OrderService",fallbackMethod = "fallBackMethod")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest){
        return service.placeOrder(orderRequest);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
            return service.getAllOrders();
    }

    public ResponseEntity<?> fallBackMethod(){
        return ResponseEntity.internalServerError().body("Service is not working currently, Circuit breaker has put service on hold");
    }
}
