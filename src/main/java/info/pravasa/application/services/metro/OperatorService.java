package info.pravasa.application.services.metro;

import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
import info.pravasa.dto.BusModelDto;
import info.pravasa.dto.OperatorDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class OperatorService {
    private final WebClient webClient;



    public OperatorService(WebClient webClient) {
            this.webClient = webClient;
        }


    public void save(OperatorDto dto){
        webClient.post().uri("http://localhost:8001/metro-rail-service/operator/save")
                .bodyValue(dto)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(OperatorDto.class).block();
    }

    public List<OperatorDto> findAll(){
        return webClient.get().uri("http://localhost:8001/metro-rail-service/operator/")
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(OperatorDto.class).collectList().block();
    }
}
