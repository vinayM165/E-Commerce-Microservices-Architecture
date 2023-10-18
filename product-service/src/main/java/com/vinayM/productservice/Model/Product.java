    package com.vinayM.productservice.Model;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.index.Indexed;
    import org.springframework.data.mongodb.core.mapping.Document;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;



    @Data
    @Document(value = "Product")
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Product {
        @Id
        private String _id;
        @Indexed(unique = true)
        private String name;
        private String description;
        private BigDecimal price;
        private Integer quantity;
        private String brand;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String imageUrl;
    }
