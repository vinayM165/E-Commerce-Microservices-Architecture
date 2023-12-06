package com.vinayM.productservice.ControllerTest;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Event.ProductAddEvent;
import com.vinayM.productservice.JsonConverter;
import com.vinayM.productservice.Model.Product;
import com.vinayM.productservice.Repository.ProductRepository;
import com.vinayM.productservice.Service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductService service;
    @Test
    public void test_get_all_products() {
        // Given
        List<Product> productList = new ArrayList<>();
        Product product1 = Product.builder()
                .name("p1")
                .description("test")
                .price(new BigDecimal(100))
                .quantity(12)
                .brand("cham-chung")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .imageUrl("image1.jpg")
                .build();
        Product product2 = Product.builder()
                .name("p2")
                .description("test")
                .price(new BigDecimal(200))
                .quantity(121)
                .brand("Phenovo")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .imageUrl("image2.jpg")
                .build();
        productList.add(product1);
        productList.add(product2);
        when(repository.findAll()).thenReturn(productList);

        // When
        ResponseEntity<List<ProductResponse>> responseEntity = service.getAllProducts();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<ProductResponse> responseList = responseEntity.getBody();
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("p1", responseList.get(0).getName());
        assertEquals("cham-chung", responseList.get(0).getBrand());
        assertEquals("test", responseList.get(0).getDescription());
        assertEquals(Optional.of(12).get(), responseList.get(0).getQuantity());
    }
    @Test
    public void test_returns_product_response_with_status_code_200_when_given_valid_product_id() {
        // Given
        String id = "valid_id";
        Product pr = Product.builder()
                .name("demo")
                .quantity(12)
                .brand("brand")
                .description("demodes")
                .price(new BigDecimal(12))
                .build();
        when(repository.findById(id)).thenReturn(Optional.of(pr));

        // When
        ResponseEntity<ProductResponse> response = service.getProduct(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pr.getName(), response.getBody().getName());
        assertEquals(pr.getPrice(),response.getBody().getPrice());
        assertEquals(pr.getBrand(),response.getBody().getBrand());
        assertEquals(pr.getQuantity(),response.getBody().getQuantity());
    }
    @Test
    public void test_delete_product_successfully() {
        // Given
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setBrand("Test Brand");
        // Mock the repository method to return a successful deletion
        doNothing().when(repository).deleteByNameAndBrand(request.getName(), request.getBrand());
        // When
        ResponseEntity<?> response = service.deleteProduct(request);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted Successfully", response.getBody());
    }
    @Test
    public void test_update_product_successfully() {
        // Given
        ProductRequest request = new ProductRequest();
        // Set necessary fields in the request
        // Mock the repository save method to return a Product object
        when(repository.save(any(Product.class))).thenReturn(new Product());
        // When
        ResponseEntity<?> response = service.updateProduct(request);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
