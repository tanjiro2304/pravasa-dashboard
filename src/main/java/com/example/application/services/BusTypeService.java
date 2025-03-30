package com.example.application.services;

import com.example.application.dto.BusType;
import com.example.application.dto.DepotDto;
import com.google.common.net.HttpHeaders;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BusTypeService {

    private final WebClient webClient;

    @Getter
    private Map<Long, BusType> busTypeMap = new HashMap<>();

    public BusTypeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<BusType> findAll() {
        List<BusType> types = Objects.requireNonNull(webClient.get().uri("http://localhost:8011/bustypes/findAll")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(BusType.class).collectList().block());
        busTypeMap = types.stream().collect(Collectors.toMap(BusType::getBusTypeId, Function.identity()));
        return types;
    }

    public void save(BusType dto) {
        BusType stringMono = webClient.post().uri("http://localhost:8011/bustypes/save")
                .bodyValue(dto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(BusType.class).block();
    }
}
