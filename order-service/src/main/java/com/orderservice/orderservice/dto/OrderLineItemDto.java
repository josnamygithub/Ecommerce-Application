package com.orderservice.orderservice.dto;

import com.orderservice.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemDto {
    private Long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;
}
