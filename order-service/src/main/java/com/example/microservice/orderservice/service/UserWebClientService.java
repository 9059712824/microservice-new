package com.example.microservice.orderservice.service;

import com.example.microservice.orderservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserWebClientService {
    private final WebClient.Builder webClientBuilder;

    public UserDto getUserById(Long userId) {
        return webClientBuilder.build()
                .get()
                .uri("http://product-service/users/user/{id}", userId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

}
