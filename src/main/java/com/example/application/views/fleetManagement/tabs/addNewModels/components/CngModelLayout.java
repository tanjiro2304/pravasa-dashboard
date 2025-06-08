package com.example.application.views.fleetManagement.tabs.addNewModels.components;

import com.example.application.dto.CngTypeDto;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

import java.util.Objects;


public class CngModelLayout extends VerticalLayout {
    private NumberField mileageField;
    private NumberField noOfTanks;
    private NumberField capacity;
    private Binder<CngTypeDto> binder;
    public CngModelLayout(){
        initFields();
        initLayout();;
        initializeBinder();
    }

    private void initLayout() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(mileageField,noOfTanks,capacity);

    }

    private void initFields() {
        mileageField = new NumberField("Mileage");
        noOfTanks = new NumberField("No Of Tanks");
        capacity = new NumberField("Capacity");
    }

    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(mileageField).bind(CngTypeDto::getMileage, CngTypeDto::setMileage);
        binder.forField(noOfTanks).bind(value -> {
                if(Objects.nonNull(value) && Objects.nonNull(value.getNoOfTanks())) {
                    return value.getNoOfTanks().doubleValue();
                }
                return 0.0;
                },
                (dto, value) -> dto.setNoOfTanks(value.intValue()));
        binder.forField(capacity).bind(CngTypeDto::getCngCapacity, CngTypeDto::setCngCapacity);
        binder.setBean(new CngTypeDto());

    }

    public CngTypeDto getBean(){
        return binder.getBean();
    }
}
