package com.example.application.services;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {

    private final WebClient webClient;

    public CompanyService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<CompanyDto> findById(Long id){

        return webClient.get()
                .uri("http://localhost:8011/company/findById/{id}", id)
                .retrieve().bodyToMono(CompanyDto.class);
    }

    public List<CompanyDto> findAll() {
        return Objects.requireNonNull(webClient.get()
                .uri("http://localhost:8011/company/findAll")
                .retrieve().bodyToFlux(CompanyDto.class).collectList().block());
    }

    public void save(CompanyDto bean) {
            CompanyDto stringMono = webClient.post().uri("http://localhost:8011/company/save")
                    .bodyValue(bean)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve().bodyToMono(CompanyDto.class).block();

    }
}
