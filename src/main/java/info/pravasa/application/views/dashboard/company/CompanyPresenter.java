package info.pravasa.application.views.dashboard.company;

import info.pravasa.application.services.CompanyService;
import info.pravasa.application.services.DepotService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.City;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import jakarta.annotation.Resource;

import java.util.List;

@UIScope
@SpringComponent
public class CompanyPresenter {

    @Resource
    private CompanyService companyService;

    @Resource
    private DepotService depotService;


    public List<Company> fetchAllCompanies(){
        return companyService.findAll();
    }

    public List<DepotDto> fetchDepotByCompany(Long id){
        return depotService.fetchDepotByCompany(id);
    }

    public void saveData (DepotDto depotDto){
        depotService.save(depotDto);
    }

    public List<City> fetchAllCities() {
        return companyService.findAllCities();
    }

    public void saveCompany(Company newCompany) {
        companyService.save(newCompany);
    }
}
