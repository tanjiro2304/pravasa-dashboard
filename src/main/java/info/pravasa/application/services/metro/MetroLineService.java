package info.pravasa.application.services.metro;

import info.pravasa.dto.MetroLineDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetroLineService {

    private WebClient webClient;

    public MetroLineService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<MetroLineDto> findAll(){
        return new ArrayList<>();
    }

    public MetroLineDto save(){
        return new MetroLineDto();
    }
}
