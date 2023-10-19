package com.vinayM.productservice.ControllerTest;

import com.vinayM.productservice.Controller.ProductController;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        // Mocking the service response
        List<ProductResponse> products = Arrays.asList(new ProductResponse(), new ProductResponse());
        when(productService.getAllProducts()).thenReturn(new ResponseEntity<>(products, HttpStatus.OK));

        // Calling the controller method
        ResponseEntity<List<ProductResponse>> response = productController.getAllProducts();

        // Verify the service method was called
        verify(productService, times(1)).getAllProducts();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    public void testGetProduct() {
        // Mocking the service response
        String productId = "123";
        ProductResponse product = new ProductResponse();
        when(productService.getProduct(productId)).thenReturn(new ResponseEntity<>(product, HttpStatus.OK));

        // Calling the controller method
        ResponseEntity<ProductResponse> response = productController.getProduct(productId);

        // Verify the service method was called
        verify(productService, times(1)).getProduct(productId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testCreateProduct() {
        // Mocking the service response
        MultipartFile image = new MockMultipartFile("image", new byte[0]);
        Map<String, String> headers = new HashMap<>();
        headers.put("Header1", "Value1");
        headers.put("Header2", "Value2");
        String name = "Product Name";
        String description = "Product Description";
        BigDecimal price = new BigDecimal("19.99");
        Integer quantity = 10;
        String brand = "Product Brand";

        ProductRequest productRequest = ProductRequest.builder()
                .image(image)
                .brand(brand)
                .price(price)
                .description(description)
                .name(name)
                .quantity(quantity)
                .build();

        when(productService.createProduct(productRequest)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        // Calling the controller method
        ResponseEntity response = productController.createProd(headers, image, name, description, price, quantity, brand);

        // Verify the service method was called
        verify(productService, times(1)).createProduct(productRequest);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
