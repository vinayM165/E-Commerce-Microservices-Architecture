package com.vinayM.productservice.Event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAddEvent {
    private String product_name;
    private String product_id;
    private int prod_quantity;
}
