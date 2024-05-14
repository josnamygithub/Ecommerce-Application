package com.orderservice.orderservice.service;

import com.orderservice.orderservice.dto.InventoryResponse;
import com.orderservice.orderservice.dto.OrderLineItemDto;
import com.orderservice.orderservice.dto.OrderRequest;
import com.orderservice.orderservice.model.Order;
import com.orderservice.orderservice.model.OrderLineItems;
import com.orderservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class OrderService {
    // static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private  final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<Object, String> kafkaTemplate;
    public String  placeOrder(OrderRequest orderRequest) {

//        if (orderRequest == null || orderRequest.getOrderLineItemsDtoList() == null) {
//            LOGGER.error("Invalid order request or order line items list is null.");
//            return ;
//        }
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);


        List<String> skuCodes =order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkucode)
                .toList();

        //Call inventory service ,and place if the order is in the stock

      InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder ->
                        uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponsesArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);

            kafkaTemplate.send("NotificationTopic",order.getOrderNumber());
            return "Order Placed Successfully";
        } else {
            throw new IllegalArgumentException("Product is not in stock , please try again later");
        }

    }
    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setSkucode(orderLineItemDto.getSkucode());

        return orderLineItems;
    }
}
