package com.orderservice.orderservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequest {
    private List<OrderLineItemDto> orderLineItemsDtoList;

}
