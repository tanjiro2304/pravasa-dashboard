package com.example.application.views.dashboard.roadTransport;

import com.example.application.services.CompanyService;
import com.example.application.services.DepotService;
import com.example.application.views.dashboard.company.CompanyView;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import jakarta.annotation.Resource;

import java.util.List;

@UIScope
@SpringComponent
public class RoadTransportPresenter {

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
}
