package com.example.application.services;


import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
import info.pravasa.dto.DepotDto;
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

    public List<DepotDto> fetchDepotByCompany(Long companyId) {
        return Objects.requireNonNull(webClient.post()
                .uri("http://localhost:8001/bus-service/depot/findAll")
                .bodyValue(companyId)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .retrieve().bodyToFlux(DepotDto.class).collectList().block());
    }

    public void save(DepotDto dto) {
        DepotDto stringMono = webClient.post().uri("http://localhost:8001/bus-service/depot/save")
                .bodyValue(dto)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(DepotDto.class).block();
    }
}
