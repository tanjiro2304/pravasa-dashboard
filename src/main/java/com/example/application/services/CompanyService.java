package com.example.application.services;


import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
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


    public List<Company> findAll() {
        return Objects.requireNonNull(webClient.post()
                .uri("http://localhost:8001/company-service/company/findAll")
                        .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .retrieve().bodyToFlux(Company.class).collectList().block());
    }

    public void save(Company bean) {
            Company stringMono = webClient.post().uri("http://localhost:8011/company/save")
                    .bodyValue(bean)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve().bodyToMono(Company.class).block();

    }

    public List<DepotDto> fetchDepotByCompany(Long companyId) {
        return Objects.requireNonNull(webClient.post()
                .uri("http://localhost:8001/bus-service/depot/findAll")
                .bodyValue(companyId)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .retrieve().bodyToFlux(DepotDto.class).collectList().block());
    }
}
