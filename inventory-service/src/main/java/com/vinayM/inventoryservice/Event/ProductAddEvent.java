package com.vinayM.inventoryservice.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAddEvent implements Serializable {
    private String product_id;
    private String product_name;
    private int prod_quantity;
}
