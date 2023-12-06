package com.vinayM.orderservice.Model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private Long customerID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private enum OrderStatus {
        CREATED,
        PROCESSING,
        SHIPPED,
        DELIVERED
    }
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;
}
