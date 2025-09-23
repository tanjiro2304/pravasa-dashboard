package info.pravasa.application.services.metro;

import com.google.common.net.HttpHeaders;
import com.vaadin.flow.server.VaadinSession;
import info.pravasa.application.views.dashboard.metro.tabs.PlanningAuthorityView;
import info.pravasa.dto.OperatorDto;
import info.pravasa.dto.PlanningAuthorityDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PlanningAuthorityService {

    private final WebClient webClient;


    public PlanningAuthorityService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void save(PlanningAuthorityDto dto) {
        webClient.post().uri("http://localhost:8001/metro-rail-service/planning-authority/save")
                .bodyValue(dto)
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToMono(PlanningAuthorityDto.class).block();
    }

    public List<PlanningAuthorityDto> findAll() {
        return webClient.get().uri("http://localhost:8001/metro-rail-service/planning-authority/")
                .header("Authorization", VaadinSession.getCurrent().getAttribute("token").toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve().bodyToFlux(PlanningAuthorityDto.class).collectList().block();
    }
}
