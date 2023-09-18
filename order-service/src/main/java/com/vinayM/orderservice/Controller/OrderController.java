package com.vinayM.orderservice.Controller;

import com.vinayM.orderservice.Model.OrderRequest;
import com.vinayM.orderservice.Model.OrderResponse;
import com.vinayM.orderservice.Service.OrderService;
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
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest){
        return service.placeOrder(orderRequest);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
            return service.getAllOrders();
    }
}
