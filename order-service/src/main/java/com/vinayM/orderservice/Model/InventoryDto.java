package com.vinayM.orderservice.Model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InventoryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @JsonDeserialize(using = StringToLongDeserializer.class)
    private String id;
    private String skuCode;
    private Integer quantity;
}