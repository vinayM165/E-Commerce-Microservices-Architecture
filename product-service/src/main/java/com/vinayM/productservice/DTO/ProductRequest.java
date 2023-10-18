package com.vinayM.productservice.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
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
    private String brand;
    private MultipartFile image;
}
