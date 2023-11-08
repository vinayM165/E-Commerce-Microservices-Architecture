package com.vinayM.productservice.Controller;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private static final Logger log = LogManager.getLogManager().getLogger("product-controller-logs");
    @Autowired
    ProductService service;
    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        try{
            return service.getAllProducts();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String id){
        try{
            return service.getProduct(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity createProd(@NotNull @RequestHeader Map<String, String> headers,
                                     @RequestParam("image") MultipartFile image,
                                     @RequestParam("name") String name,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") BigDecimal price,
                                     @RequestParam("quantity") Integer quantity,
                                     @RequestParam("brand") String brand)
    {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
    try{
        ProductRequest request = ProductRequest.builder()
                        .image(image)
                                .brand(brand)
                                        .price(price)
                                                .description(description)
                                                        .name(name)
                                                                .quantity(quantity)
                                                                        .build();
       // log.info("Object Received  is :" + request.toString());
        return service.createProduct(request);
    }catch (Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteEntityWithID(@RequestBody ProductRequest request){
        return service.deleteProduct(request);
    }

    @PutMapping
    public ResponseEntity<?> updateEntity(@RequestBody  ProductRequest request){
        return service.updateProduct(request);
    }


}
