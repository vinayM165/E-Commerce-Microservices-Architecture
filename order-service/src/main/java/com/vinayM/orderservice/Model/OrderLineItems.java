package com.vinayM.orderservice.Model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
    @Table(name = "t_order_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;
}
