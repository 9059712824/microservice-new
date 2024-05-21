package com.example.microservice.orderservice.dto;

import com.example.microservice.orderservice.Enum.OrderStatus;
import com.example.microservice.orderservice.model.OrderedItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderResponseDto {
    private Long id;
    private String orderNumber;
    private OrderStatus orderStatus;
    private LocalDateTime purchasedDate;
    private List<OrderItemResponseDto> orderedItemsList;
    private Long userId;
}
