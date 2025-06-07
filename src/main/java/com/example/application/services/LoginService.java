package com.example.application.services;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.dto.LoginUserDto;
import com.google.common.net.HttpHeaders;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class LoginService {

    private final WebClient webClient;

    public LoginService(WebClient webClient) {
        this.webClient = webClient;
    }

    public LoginUserDto login(LoginUserDto loginUserDto){

        return Objects.requireNonNull(webClient.post().uri("http://localhost:8011/login")
                .bodyValue(loginUserDto)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(LoginUserDto.class).block());
    }
}
