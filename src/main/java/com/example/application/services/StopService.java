package com.example.application.services;

import com.example.application.dto.DepotDto;
import com.example.application.dto.RouteDto;
import com.example.application.dto.StopDto;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StopService {
    private final WebClient webClient;

    public StopService(WebClient webClient) {
        this.webClient = webClient;
    }
    public void save(StopDto dto) {
        webClient.post().uri("http://localhost:8011/stop/save")
                .bodyValue(dto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(StopDto.class).block();
    }
}
