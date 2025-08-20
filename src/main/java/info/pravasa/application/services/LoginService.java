package info.pravasa.application.services;


import com.google.common.net.HttpHeaders;
import info.pravasa.dto.UserInfo;

import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Objects;

@Service
public class LoginService {

    private final WebClient webClient;

    public LoginService(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserInfo login(UserInfo userInfo){

        return Objects.requireNonNull(webClient.post().uri("http://localhost:8001/user/login")
                .bodyValue(userInfo)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(UserInfo.class).block());
    }
}
