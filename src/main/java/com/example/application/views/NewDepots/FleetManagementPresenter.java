package com.example.application.views.NewDepots;

import com.example.application.dto.BusType;
import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.services.BusTypeService;
import com.example.application.services.CompanyService;
import com.example.application.services.DepotService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

@SpringComponent
public class FleetManagementPresenter {

    @Autowired
    private ApplicationContext applicationContext;

    private FleetManagementView fleetManagementView;

    public FleetManagementPresenter() {

    }
}
