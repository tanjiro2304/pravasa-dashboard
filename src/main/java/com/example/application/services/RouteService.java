package com.example.application.services;

import com.example.application.dto.DepotDto;
import com.example.application.dto.RouteDto;
import com.example.application.dto.filter.RouteFilter;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

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

    public List<RouteDto> findByCompanyId(RouteFilter routeFilter) {
        return Objects.requireNonNull(webClient.post().uri("http://localhost:8011/route/findByCompanyId")
                .bodyValue(routeFilter)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(RouteDto.class).collectList().block());
    }
}
