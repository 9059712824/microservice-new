package com.example.microservice.orderservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
 private Long userId;
 private List<OrderedItemsRequest> orderedItemsDtosList;

}
