package com.orderservice.orderservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlaceEvent {

    private String orderNumber;


}
