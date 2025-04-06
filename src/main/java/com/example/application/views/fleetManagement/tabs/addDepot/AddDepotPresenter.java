package com.example.application.views.fleetManagement.tabs.addDepot;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.services.CompanyService;
import com.example.application.services.DepotService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Resource;

import java.util.List;

@SpringComponent
public class AddDepotPresenter {

    @Resource
    public CompanyService companyService;

    @Resource
    public DepotService depotService;

    public List<CompanyDto> getCompanies() {
        return companyService.findAll();
    }

    public List<DepotDto> findDepotByCompanyId(Long companyId) {
        return depotService.findDepotByCompanyId(companyId);
    }

//    public void onSave(DepotDto dto) {
//        FleetManagementView view = applicationContext.getBean(FleetManagementView.class);
//        depotService.save(dto);
//        view.setDataInGrid();
//    }
}
