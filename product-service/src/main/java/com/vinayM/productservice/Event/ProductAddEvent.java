package com.vinayM.productservice.Event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAddEvent implements Serializable {
    private String product_id;
    private String product_name;
    private int prod_quantity;
}
