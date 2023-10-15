package com.vinayM.orderservice.Model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderRequest {
    private List<OrderLineItems> orderLineItemsList;
}
