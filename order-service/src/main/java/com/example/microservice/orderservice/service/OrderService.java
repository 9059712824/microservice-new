package com.example.microservice.orderservice.service;

import com.example.microservice.orderservice.dto.OrderRequest;
import com.example.microservice.orderservice.model.Order;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface OrderService {
     Order placeholder(OrderRequest request);
     List<Order> getAllOrders();
     List<Order> getOrdersByUserId(Long userId);

}
