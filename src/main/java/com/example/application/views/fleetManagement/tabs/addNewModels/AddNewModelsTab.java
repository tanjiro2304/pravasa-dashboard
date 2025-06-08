package com.example.application.views.fleetManagement.tabs.addNewModels;

import com.example.application.dto.BusDto;
import com.example.application.views.fleetManagement.tabs.addNewModels.components.CngModelLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class AddNewModelsTab extends Tab {
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

    private Binder<BusDto> binder;

    private Button saveButton;

    private Button clearButton;

    private H3 title;

    private VerticalLayout mainLayout;

    private CngModelLayout cngModelLayout;

    public AddNewModelsTab(AddNewModelsPresenter presenter) {
        this.presenter = presenter;
        initializeFields();
        initializeBinder();
        setupLayout();
        setListeners();
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

        saveButton = new Button("Save", event ->{
            BusDto bean = binder.getBean();
            bean.setBusType(cngModelLayout.getBean());
            presenter.onSave(bean);
        });
        clearButton = new Button("Clear");
    }


    private void initializeBinder() {
        binder = new Binder<>();
        binder.setBean(new BusDto());
        binder.forField(modelName).bind(BusDto::getModelName, BusDto::setModelName);
        binder.forField(manufacturer).bind(BusDto::getManufacturer, BusDto::setManufacturer);
        binder.forField(isAirConditioned).bind(BusDto::getIsAirConditioned, BusDto::setIsAirConditioned);
        binder.forField(floorHeight).bind(BusDto::getFloorType, BusDto::setFloorType);
    }

    private void setupLayout() {
        mainLayout = new VerticalLayout();

        mainLayout.add(
                createRow(title),
                createRow(modelName, manufacturer),
                createRow(isElectric, isCNG,isDiesel),
                createRow(floorHeight),
                createRow(saveButton, clearButton)
        );
        mainLayout.setPadding(true);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(mainLayout);
    }

    private HorizontalLayout createRow(Component... components) {
        return new HorizontalLayout(components);
    }

    private void setListeners(){
        isCNG.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() && !containsComponent(mainLayout.getChildren().toList(), cngModelLayout)){
                cngModelLayout = new CngModelLayout();
                mainLayout.addComponentAtIndex(4, cngModelLayout);
            }else{
                if(containsComponent(mainLayout.getChildren().toList(), cngModelLayout)) {
                    mainLayout.remove(cngModelLayout);
                }
            }
        });
    }

    public boolean containsComponent(List<Component> components, Component component){
        return components.stream().anyMatch(c -> c.equals(component));
    }

}
