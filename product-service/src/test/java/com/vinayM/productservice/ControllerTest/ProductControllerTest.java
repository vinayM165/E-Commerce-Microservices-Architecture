package com.vinayM.productservice.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinayM.productservice.Controller.ProductController;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Repository.ProductRepository;
import com.vinayM.productservice.Service.ProductService;
import kotlin.jvm.internal.unsafe.MonitorKt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ProductService service;

  @InjectMocks
  ProductController productController;

  @Test
    public void testGetAllProducts() throws Exception{
      ProductResponse rp = ProductResponse.builder()
              .name("p1")
              .price(new BigDecimal(100))
              .brand("cham-chung")
              .description("test")
              .quantity(12)
              .build();
      ProductResponse rp1 = ProductResponse.builder()
              .name("p2")
              .price(new BigDecimal(200))
              .brand("Phenovo")
              .description("test")
              .quantity(121)
              .build();
      List<ProductResponse> rLi = new ArrayList<>();
      rLi.add(rp);
      rLi.add(rp1);
        when(service.getAllProducts()).thenReturn(ResponseEntity.ok(rLi));
      mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8282/api/product")
              .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("p1"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand").value("cham-chung"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("test"))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(12));
  }
  @Test
  public void testGetProduct() throws Exception{
    String id = "id1";
    ProductResponse pr = ProductResponse.builder()
            .name("demo")
            .quantity(12)
            .brand("brand")
            .description("demodes")
            .price(new BigDecimal(12))
            .build();
    when(service.getProduct(id)).thenReturn(ResponseEntity.ok(pr));
    mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8282/api/product/{id}",id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("demo"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(12))
            .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("brand"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("demodes"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(12));
  }
  @Test
  public void testCreateProd() throws Exception{
    Map<String, String> headers = new HashMap<>();
    headers.put("header1", "value1");
    headers.put("header2", "value2");
    MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[0]);
    String name = "Product Name";
    String description = "Product Description";
    BigDecimal price = new BigDecimal("10.00");
    Integer quantity = 5;
    String brand = "Product Brand";
    ProductRequest pr = ProductRequest.builder()
                    .name(name)
                            .quantity(quantity)
                                    .price(price)
                                            .description(description)
                                                    .brand(brand).build();
    when(service.createProduct(pr)).thenReturn(new ResponseEntity(HttpStatus.CREATED));
    mockMvc.perform(MockMvcRequestBuilders.multipart("http://localhost:8282/api/product")
                    .file(image)
                    .param("name", name)
                    .param("description", description)
                    .param("price", price.toString())
                    .param("quantity", quantity.toString())
                    .param("brand", brand))
            .andExpect(status().isOk())
            .andExpect(result -> {
      MvcResult mvcResult = result;
      System.out.println("Response Body: " + mvcResult.getResponse().getContentAsString());
    }).andDo(MockMvcResultHandlers.print());;
  }


  @Test
  public void testDeleteEntityWithID() throws Exception {
    // Create a ProductRequest for testing
    ProductRequest request = new ProductRequest();
    // Set necessary fields in the request

    // Mock the service method to return a ResponseEntity
    when(service.deleteProduct(request)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

    // Perform the DELETE request using MockMvc
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
              MvcResult mvcResult = result;
              System.out.println("Response Body: " + mvcResult.getResponse().getContentAsString());
              // Add additional assertions if needed
            });
  }
  @Test
  public void testUpdateEntity() throws Exception {
    // Create a ProductRequest for testing
    ProductRequest request = new ProductRequest();
    // Set necessary fields in the request

    // Mock the service method to return a ResponseEntity
    when(service.updateProduct(request)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

    // Perform the PUT request using MockMvc
    mockMvc.perform(MockMvcRequestBuilders.put("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(result -> {
              MvcResult mvcResult = result;
              System.out.println("Response Body: " + mvcResult.getResponse().getContentAsString());
              // Add additional assertions if needed
            });
  }
}
