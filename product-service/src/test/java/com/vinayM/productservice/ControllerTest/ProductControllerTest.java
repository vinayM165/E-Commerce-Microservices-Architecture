package com.vinayM.productservice.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinayM.productservice.Controller.ProductController;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Model.Product;
import com.vinayM.productservice.Service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProductService service;

    @Test
    public void testGetProductByID() throws Exception {
        long prodID = 1L;
        ProductResponse p = ProductResponse.builder()
                .description("sample prod reponse")
                        .build();
        Mockito.when(service.getProduct(prodID)).thenReturn(new ResponseEntity<>(p, HttpStatus.OK));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{id}",prodID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(service,Mockito.times(1)).getProduct(prodID);
    }

    @Test
    public void testGetAllProducts() throws Exception{
        List<ProductResponse> productList = Arrays.asList(
                ProductResponse.builder().name("bla bla").build(),
                ProductResponse.builder().name("aa aa").build(),
                ProductResponse.builder().name("la la").build());
        Mockito.when(service.getAllProducts()).thenReturn( ResponseEntity.ok(productList));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(service,Mockito.times(1)).getAllProducts();
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductRequest p = ProductRequest.builder().build();
        Mockito.when(service.createProduct(p)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                            .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}

