//package com.vinayM.orderservice.Controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.vinayM.orderservice.Model.Order;
//import com.vinayM.orderservice.Model.OrderRequest;
//import com.vinayM.orderservice.Model.OrderResponse;
//import com.vinayM.orderservice.Service.OrderService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Stream;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(OrderController.class)
//public class ControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    OrderService service;
//    @Test
//    public void testPlaceOrder() throws Exception{
//        OrderRequest request = OrderRequest.builder().build();
//        Mockito.when(service.placeOrder(request)).thenReturn(new ResponseEntity(HttpStatus.CREATED));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(request)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//
//        Mockito.verify(service,Mockito.times(1)).placeOrder(request);
//    }
//    @Test
//    public  void testGetAllOrders() throws Exception{
//        List<OrderResponse> responses = Arrays.asList(
//                OrderResponse.builder().build(),
//                OrderResponse.builder().build(),
//                OrderResponse.builder().build());
//        Mockito.when(service.getAllOrders()).thenReturn(ResponseEntity.ok(responses));
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/order"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//        Mockito.verify(service,Mockito.times(1)).getAllOrders();
//    }
//}
