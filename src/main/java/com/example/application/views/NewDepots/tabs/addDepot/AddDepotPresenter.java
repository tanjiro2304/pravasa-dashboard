package com.example.application.views.NewDepots.tabs.addDepot;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.services.CompanyService;
import com.example.application.services.DepotService;
import com.example.application.views.NewDepots.FleetManagementView;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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

    public List<DepotDto> getDataForGrid() {
        return depotService.findDepotByCompanyId(1L);
    }

//    public void onSave(DepotDto dto) {
//        FleetManagementView view = applicationContext.getBean(FleetManagementView.class);
//        depotService.save(dto);
//        view.setDataInGrid();
//    }
}
