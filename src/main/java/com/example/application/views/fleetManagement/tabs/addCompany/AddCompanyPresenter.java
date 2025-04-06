package com.example.application.views.fleetManagement.tabs.addCompany;

import com.example.application.dto.CompanyDto;
import com.example.application.services.CompanyService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.Resource;

import java.util.List;

@SpringComponent
public class AddCompanyPresenter {

    @Resource
    private CompanyService companyService;

    public void saveCompany(CompanyDto companyDto){
        companyService.save(companyDto);
    }

    public List<CompanyDto> findAll(){
        return companyService.findAll();
    }
}
