package com.vinayM.inventoryservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {
    private Long id;
    private String skuCode;
    private int quantity;
}
