//package com.vinayM.productservice.RepositoryTest;
//
//import com.vinayM.productservice.DTO.ProductResponse;
//import com.vinayM.productservice.Model.Product;
//import com.vinayM.productservice.Repository.ProductRepository;
//import org.junit.Assert;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import org.junit.Test;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
//public class ProductRepoTest {
////    @Autowired
////    private TestEntityManager entityManager;
////    @Autowired
////    ProductRepository repository;
////
////    @Test
////   public void testGetAllProducts(){
////        List<Product> pList = repository.findAll();
////        Assert.assertFalse(pList.isEmpty());
////    }
////
////    @Test
////    public void testGetProductById(){
////        int id = 1;
////        Optional<Product> p = repository.findById(1L);
////        Long l = p.get().getId();
////        Assert.assertTrue(l==1L);
////    }
////
////    @Test
////    public void testCreateProduct(){
////       Product p = Product.builder()
////                .name("Ikea table")
////                .price(BigDecimal.valueOf(4000))
////                .description("ikea table")
////                .quantity(1).build();
////
////        repository.save(p);
////        Product response = entityManager.find(Product.class, p.getId());
////        Assert.assertEquals(p.getId(),response.getId());
////    }
//}
