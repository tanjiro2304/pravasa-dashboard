package com.example.application.views.routeManagement.tabs.addnewroute;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.RouteDto;
import com.example.application.dto.filter.RouteFilter;
import com.example.application.services.CompanyService;
import com.example.application.services.DepotService;
import com.example.application.services.RouteService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Resource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringComponent
public class AddNewRoutePresenter {

    @Resource
    private CompanyService companyService;

    @Resource
    private RouteService routeService;

    public List<CompanyDto> getCompanies() {
        return companyService.findAll();
    }

    public List<RouteDto> findByCompanyId(Long id){
        return routeService.findByCompanyId(RouteFilter.builder()
                .companyId(id)
                .build());
    }
}
