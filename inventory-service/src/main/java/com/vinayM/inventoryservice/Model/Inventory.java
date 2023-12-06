package com.vinayM.inventoryservice.Model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "t_inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String skuCode;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}