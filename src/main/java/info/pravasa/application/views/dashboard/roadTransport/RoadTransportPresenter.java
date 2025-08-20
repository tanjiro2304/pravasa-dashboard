package info.pravasa.application.views.dashboard.roadTransport;

import info.pravasa.application.services.BusModelService;
import info.pravasa.application.services.CompanyService;
import info.pravasa.application.services.DepotService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.BusModelDto;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.filters.BusModelFilter;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
public class RoadTransportPresenter {

    @Resource
    private CompanyService companyService;

    @Resource
    private DepotService depotService;

    @Resource
    private BusModelService busModelService;


    public List<Company> fetchAllCompanies(){
        return companyService.findAll();
    }

    public List<DepotDto> fetchDepotByCompany(Long id){
        if(Objects.isNull(id)){
            return new ArrayList<>();
        }
        return depotService.fetchDepotByCompany(id);
    }

    public void saveData (DepotDto depotDto){
        depotService.save(depotDto);
    }

    public void saveBusModel(BusModelDto busModelDto) {
        busModelService.save(busModelDto);
    }

    public List<BusModelDto> fetchAllModels() {
        return busModelService.fetchAllModels(new BusModelFilter());
    }
}
