package com.vinayM.productservice.Repository;

import com.vinayM.productservice.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    boolean existByName(String name);
    Optional<Product> findByName(String name);
    void deleteByNameAndBrand(String name,String brand);
}
