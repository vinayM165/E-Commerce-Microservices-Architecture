package com.vinayM.inventoryservice.Model;


import lombok.*;

import javax.persistence.*;


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
    private String id;
    @Column(unique = true)
    private String skuCode;
    private int quantity;
}