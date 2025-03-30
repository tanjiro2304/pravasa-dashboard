package com.example.application.views.NewRoutes;

import com.example.application.dto.BusType;
import com.example.application.dto.CompanyDto;
import com.example.application.dto.StopDto;
import com.example.application.services.BusTypeService;
import com.example.application.services.CompanyService;
import com.example.application.services.StopService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringComponent
public class RouteManagementPresenter {

    private final CompanyService companyService;

    private final BusTypeService busTypeService;

    private final StopService stopService;

    private Map<Long, CompanyDto> companyDtoMap;

    public RouteManagementPresenter(CompanyService companyService, BusTypeService busTypeService, StopService stopService) {
        this.companyService = companyService;
        this.busTypeService = busTypeService;
        this.stopService = stopService;
    }

    public List<CompanyDto> getCompanies() {
        companyDtoMap = companyService.findAll().stream().collect(Collectors.toMap(CompanyDto::getId, Function.identity()));
        return companyService.findAll();
    }

    public List<BusType> getBusTypes(){ return busTypeService.findAll(); }

    public CompanyDto findById(Long id){
        return companyDtoMap.get(id);
    }


    public void saveStop(StopDto bean) {
        stopService.save(bean);
    }
}
