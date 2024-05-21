package com.example.microservice.orderservice.dto;

import com.example.microservice.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String message;
    private PlaceOrderResponseDto order;
}

