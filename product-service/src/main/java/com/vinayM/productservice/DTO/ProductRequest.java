package com.vinayM.productservice.DTO;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;


}
