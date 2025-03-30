package com.example.application.services;

import com.example.application.dto.DepotDto;
import com.example.application.dto.filter.RouteFilter;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class DepotService {
    private final WebClient webClient;

    public DepotService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<DepotDto> findDepotByCompanyId(Long id){
        RouteFilter routeFilter = RouteFilter.builder()
                .companyId(id)
                .build();

        return Objects.requireNonNull(webClient.post().uri("http://localhost:8011/depot/findAllDepots")
                        .bodyValue(routeFilter)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(DepotDto.class).collectList().block());
    }

    public void save(DepotDto dto) {
        DepotDto stringMono = webClient.post().uri("http://localhost:8011/depot/save")
                .bodyValue(dto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(DepotDto.class).block();
    }
}
