package com.example.application.views.fleetManagement.tabs.addNewModels;

import com.example.application.dto.BusDto;
import com.example.application.services.BusTypeService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class AddNewModelsPresenter {

    private final BusTypeService busTypeService;

    public AddNewModelsPresenter(BusTypeService busTypeService) {
        this.busTypeService = busTypeService;
    }

    public void onSave(BusDto busDto){
        busTypeService.save(busDto);
    }
}
