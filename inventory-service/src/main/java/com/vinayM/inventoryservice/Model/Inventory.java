package com.vinayM.inventoryservice.Model;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Constraint;


@Entity
@Table
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
    private Integer quantity;
}