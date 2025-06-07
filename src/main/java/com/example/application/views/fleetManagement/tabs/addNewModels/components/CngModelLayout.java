package com.example.application.views.fleetManagement.tabs.addNewModels.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;

public class CngModelLayout extends VerticalLayout {
    private NumberField mileageField;
    private NumberField noOfTanks;
    private NumberField capacity;

    public CngModelLayout(){
        initFields();
        initLayout();;
    }

    private void initLayout() {
        add(mileageField,noOfTanks,capacity);

    }

    private void initFields() {
        mileageField = new NumberField("Mileage");
        noOfTanks = new NumberField("No Of Tanks");
        capacity = new NumberField("Capacity");
    }
}
