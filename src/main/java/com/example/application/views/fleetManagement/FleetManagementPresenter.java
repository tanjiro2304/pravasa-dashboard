package com.example.application.views.fleetManagement;

import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@SpringComponent
public class FleetManagementPresenter {

    @Autowired
    private ApplicationContext applicationContext;

    private FleetManagementView fleetManagementView;

    public FleetManagementPresenter() {

    }
}
