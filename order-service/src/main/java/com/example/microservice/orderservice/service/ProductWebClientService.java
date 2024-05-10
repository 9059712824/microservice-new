package com.example.microservice.orderservice.service;

import com.example.microservice.orderservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductWebClientService {
    private final WebClient.Builder webClientBuilder;

    public InventoryResponse[] checkStock(List<String> names) {
        String url = "http://product-service:8080/inventory/checkStock?name=" + String.join(",", names);
        log.info("Request URL: {}", url);

        return webClientBuilder.build().get()
                .uri(url)
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .onErrorResume(error -> {
                    log.error("Failed to retrieve inventory information from response: {}", error.getMessage());
                    return Mono.empty();
                })
                .block();
    }
}
