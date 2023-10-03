package com.vinayM.productservice.Service;

import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Event.ProductAddEvent;
import com.vinayM.productservice.Model.Product;
import com.vinayM.productservice.Repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private KafkaTemplate<String, ProductAddEvent> template;
    private static final Logger log = LoggerFactory.getLogger("ServiceLogs");
    @Autowired
    private ProductRepository repository;
    public ResponseEntity createProduct(@RequestBody ProductRequest productRequest){
        try {
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .quantity(productRequest.getQuantity()).build();
            log.info("Product is getting saved....!!!");
            Product p = repository.save(product);
            log.info("product is saved!!!");
            log.info("starting Kafka event productAdd!!!");
            template.send("ProductAddTopic",new ProductAddEvent(p.getName(),p.getId().toString(),p.getQuantity()));
            log.info("kafka event message has been sent to topic successfully!");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        try{
            List<Product> list = repository.findAll();
            List<ProductResponse> rList = new ArrayList<>();
            for(Product p : list){
                ProductResponse response = ProductResponse.builder()
                        .name(p.getName())
                        .description(p.getDescription())
                        .price(p.getPrice()).build();
                rList.add(response);
            }
            return new ResponseEntity<>(rList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ProductResponse> getProduct(long id){
        try{
            Optional<Product> res = repository.findById(id);
           return res.map(product -> new ResponseEntity<>(mapToProductResponse(product),HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice()).build();
    }
}
