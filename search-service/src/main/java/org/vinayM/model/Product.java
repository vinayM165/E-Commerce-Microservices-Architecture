package org.vinayM.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(indexName = "product", createIndex = true)
public class Product {
    @Id
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String brand;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String imageUrl;
}
