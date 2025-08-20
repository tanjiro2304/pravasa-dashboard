package info.pravasa.application.services;

import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
import info.pravasa.dto.BusModelDto;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.filters.BusModelFilter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class BusModelService {
    private final WebClient webClient;

    public BusModelService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void save(BusModelDto dto) {

        webClient.post().uri("http://localhost:8001/bus-service/busModel/save")
                .bodyValue(dto)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(BusModelDto.class).block();
    }

    public List<BusModelDto> fetchAllModels(BusModelFilter filter) {
        return Objects.requireNonNull(webClient.post().uri("http://localhost:8001/bus-service/busModel/findAll")
                .bodyValue(filter)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(BusModelDto.class).collectList().block());
    }
}
