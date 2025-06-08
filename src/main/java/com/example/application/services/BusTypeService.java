package com.example.application.services;

import com.example.application.dto.BusDto;
import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
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
    private Map<Long, BusDto> busTypeMap = new HashMap<>();

    public BusTypeService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<BusDto> findAll() {
        List<BusDto> types = Objects.requireNonNull(webClient.get().uri("http://localhost:8011/bustypes/findAll")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(BusDto.class).collectList().block());
        busTypeMap = types.stream().collect(Collectors.toMap(BusDto::getBusTypeId, Function.identity()));
        return types;
    }

    public void save(BusDto dto) {
        webClient.post().uri("http://localhost:8011/bustypes/save")
                .bodyValue(dto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + VaadinSession.getCurrent().getAttribute("token"))
                .retrieve().bodyToMono(BusDto.class).block();
    }
}
