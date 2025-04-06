package com.example.application.views.fleetManagement.tabs.addNewModels;

import com.example.application.dto.BusType;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AddNewModelsTab extends VerticalLayout {
    private final AddNewModelsPresenter presenter;

    private TextField modelName;

    private TextField manufacturer;

    private Checkbox isAirConditioned;

    private NumberField busLength;

    private NumberField busHeight;

    private NumberField rangeInKm;

    private RadioButtonGroup<String> floorHeight;

    private Checkbox isElectric;

    private Checkbox isCNG;

    private Checkbox isDiesel;

    private RadioButtonGroup<String> emissionNorms;

    private RadioButtonGroup<String> fleetOwnerShip;

    private Binder<BusType> busTypeBinder;

    private Button saveButton;

    private Button clearButton;

    private H3 title;

    public AddNewModelsTab(AddNewModelsPresenter presenter) {
        setDimensions();
        this.presenter = presenter;
        initializeFields();
        initializeBinder();
        setupLayout();
    }

    private void setDimensions() {
        setHeight("65%");
        setWidth("50%");
    }

    private void initializeFields() {
        title = new H3("Add New Bus Type");
        modelName = new TextField("Model Name");
        manufacturer = new TextField("Manufacturer");
        isAirConditioned = new Checkbox("Air Conditioned");
        busLength = new NumberField("Bus Length (mm)");
        busHeight = new NumberField("Bus Height (mm)");
        rangeInKm = new NumberField("Range (km)");

        floorHeight = new RadioButtonGroup<>();
        floorHeight.setLabel("Floor Height");
        floorHeight.setItems("Low Floor", "Semi-Low Floor", "High Floor");

        isElectric = new Checkbox("Electric");
        isCNG = new Checkbox("CNG");
        isDiesel = new Checkbox("Diesel");

        emissionNorms = new RadioButtonGroup<>();
        emissionNorms.setLabel("Emission Norms");
        emissionNorms.setItems("BS-IV", "BS-VI");

        fleetOwnerShip = new RadioButtonGroup<>();
        fleetOwnerShip.setLabel("Fleet Ownership");
        fleetOwnerShip.setItems("Owned", "Wet Lease");
        saveButton = new Button("Save");
        clearButton = new Button("Clear");
//        setListenersForButtons();
    }


    private void initializeBinder() {
        busTypeBinder = new Binder<>();
        busTypeBinder.setBean(new BusType());
        busTypeBinder.forField(modelName).bind(BusType::getModelName, BusType::setModelName);
        busTypeBinder.forField(manufacturer).bind(BusType::getManufacturer, BusType::setManufacturer);
        busTypeBinder.forField(isElectric).bind(BusType::getIsElectric, BusType::setIsElectric);
        busTypeBinder.forField(isCNG).bind(BusType::getIsCNG, BusType::setIsCNG);
        busTypeBinder.forField(isDiesel).bind(BusType::getIsDiesel, BusType::setIsDiesel);
        busTypeBinder.forField(isAirConditioned).bind(BusType::getIsAirConditioned, BusType::setIsAirConditioned);
        busTypeBinder.forField(busLength).bind(BusType::getLength, BusType::setLength);
        busTypeBinder.forField(busHeight).bind(BusType::getHeight, BusType::setHeight);
        busTypeBinder.forField(floorHeight).bind(BusType::getFloorHeight, BusType::setFloorHeight);
        busTypeBinder.forField(emissionNorms).bind(BusType::getEmissionNorms, BusType::setEmissionNorms);
        busTypeBinder.forField(fleetOwnerShip).bind(BusType::getFleetOwnership, BusType::setFleetOwnership);
        busTypeBinder.forField(rangeInKm).bind(BusType::getRangeInKm, BusType::setRangeInKm);
    }

    private void setupLayout() {
        VerticalLayout mainLayout = new VerticalLayout();

        mainLayout.add(
                createRow(title),
                createRow(modelName, manufacturer),
                createRow(isElectric, isCNG,isDiesel,isAirConditioned),
                createRow(busLength, busHeight,rangeInKm),
                createRow(floorHeight, emissionNorms),
                createRow(fleetOwnerShip),
                createRow(saveButton, clearButton)
        );
        mainLayout.setPadding(true);
        mainLayout.setSpacing(true);
        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(mainLayout);
    }

    private HorizontalLayout createRow(Component... components) {
        return new HorizontalLayout(components);
    }

//    private void setListenersForButtons(){
//        saveButton.addClickListener(event -> {
//           presenter.saveBusType(busTypeBinder.getBean());
//           busTypeBinder.setBean(new BusType());
//            Notification.show("Data saved for bus type.");
//        });
//    }
}
