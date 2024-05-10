package com.example.microservice.orderservice.service;

import com.example.microservice.orderservice.Enum.OrderStatus;
import com.example.microservice.orderservice.Exception.NotFoundException;
import com.example.microservice.orderservice.dto.InventoryResponse;
import com.example.microservice.orderservice.dto.OrderRequest;
import com.example.microservice.orderservice.dto.OrderedItemsRequest;
import com.example.microservice.orderservice.dto.UserDto;
import com.example.microservice.orderservice.model.Order;
import com.example.microservice.orderservice.model.OrderedItems;
import com.example.microservice.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ProductWebClientService productWebClientService;
    private final  UserWebClientService userWebClientService;
    public Order placeholder(OrderRequest request) {

        UserDto userDto = userWebClientService.getUserById(request.getUserId());

        if (userDto == null) {
            throw new NotFoundException("User not found with ID: " + request.getUserId());
        }
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setPurchasedDate(LocalDateTime.now());
        order.setUserId(request.getUserId());

        // Populate ordered items
        List<OrderedItems> orderedItems = new ArrayList<>();
        List<OrderedItemsRequest> orderedItemsList = request.getOrderedItemsDtosList();
        if (orderedItemsList != null) {
            orderedItems = orderedItemsList.stream()
                    .map(orderedItemsRequest -> mapToOrderItemsReq(orderedItemsRequest))
                    .collect(Collectors.toList());
        }
        order.setOrderedItemsList(orderedItems);

        // Fetch product names
        List<String> names = orderedItemsList.stream()
                .map(OrderedItemsRequest::getName)
                .collect(Collectors.toList());

        log.info("SKU codes extracted from the order: {}", names);

        InventoryResponse[] productResponses = productWebClientService.checkStock(names);

        if (productResponses == null || productResponses.length == 0) {
            throw new IllegalStateException("Failed to retrieve inventory information.");
        }

        for (OrderedItems orderedItem : order.getOrderedItemsList()) {
            String name = orderedItem.getName();
            Optional<InventoryResponse> productResponse = Arrays.stream(productResponses)
                    .filter(response -> response != null && response.getName().equals(name))
                    .findFirst();

            if (productResponse.isEmpty() || !productResponse.get().isInStock()) {
                throw new IllegalArgumentException("Product with SKU: " + name + " is out of stock!");
            }

            Long orderedQuantity = orderedItem.getQuantity();
            Long availableQuantity = productResponse.get().getQuantity();
            if (orderedQuantity > availableQuantity) {
                throw new IllegalArgumentException("Insufficient quantity for product with SKU: " + name);
            }
        }

        return orderRepo.save(order);
    }


    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

   public List<Order> getOrdersByUserId(Long userId) {
    UserDto userDto = userWebClientService.getUserById(userId);

    if (userDto != null) {
        return orderRepo.findByUserId(userDto.getId());
    } else {
        throw new NotFoundException("User not found with ID: " + userId);
    }
}

    private OrderedItems mapToOrderItemsReq(OrderedItemsRequest itemsRequest) {
        OrderedItems orderedItems = new OrderedItems();
        orderedItems.setQuantity(itemsRequest.getQuantity());
        orderedItems.setPrice(itemsRequest.getPrice());
        orderedItems.setName(itemsRequest.getName());
        orderedItems.setProductId(itemsRequest.getProductId());
        return orderedItems;
    }
}
