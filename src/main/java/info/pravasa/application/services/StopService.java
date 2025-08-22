package info.pravasa.application.services;

import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
import info.pravasa.dto.Company;
import info.pravasa.dto.StopDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class StopService {
    private final WebClient webClient;

    public StopService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void save(StopDto bean) {
        webClient.post().uri("http://localhost:8001/bus-service/stop/save")
                .bodyValue(bean)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(StopDto.class).block();
    }

    public List<StopDto> findAll(Long companyId) {
        if(Objects.isNull(companyId)){
            return Collections.emptyList();
        }
        return Objects.requireNonNull(webClient.post().uri("http://localhost:8001/bus-service/stop/findAll")
                .bodyValue(companyId)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(StopDto.class).collectList().block());
    }

    public void saveJson(String json) {
        webClient.post()
                .uri("http://localhost:8001/bus-service/stop/saveAll")
                .bodyValue(json) // sending raw string
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class) // response type (could also be StopDto.class if response is mapped)
                .block();
    }
}
