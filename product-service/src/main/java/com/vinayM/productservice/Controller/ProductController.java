package com.vinayM.productservice.Controller;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService service;
    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        try{
            return service.getAllProducts();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id){
        try{
            return service.getProduct(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity createProd(@RequestBody ProductRequest request){
        try{
            return service.createProduct(request);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
