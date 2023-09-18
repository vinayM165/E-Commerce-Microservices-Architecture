package com.vinayM.orderservice.RepoTest;

import com.vinayM.orderservice.Model.Order;
import com.vinayM.orderservice.Model.OrderRequest;
import com.vinayM.orderservice.Model.OrderResponse;
import com.vinayM.orderservice.Repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepoTest {
    @Autowired
    EntityManager manager;

    @Autowired
    OrderRepository repository;

    @Test
    public void testPlaceOrder(){
        Order request = Order.builder().build();
        repository.save(request);
        Order order = manager.find(Order.class, request.getId());
        Assert.assertEquals(request.getId(),order.getId());
    }

    @Test
    public void testGetAll(){
        List<Order> pList = repository.findAll();
        Assert.assertFalse(pList.isEmpty());
    }
}
