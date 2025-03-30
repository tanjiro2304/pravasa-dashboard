package com.example.application.services;

import com.example.application.dto.DepotDto;
import com.example.application.dto.RouteDto;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RouteService {
    private final WebClient webClient;

    public RouteService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void save(DepotDto dto) {
        webClient.post().uri("http://localhost:8011/route/save")
                .bodyValue(dto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(RouteDto.class).block();
    }
}
