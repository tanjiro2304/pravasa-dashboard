package com.example.application.views.components;

import com.example.application.dto.BusType;
import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.dto.vo.BusTypeVO;
import com.example.application.views.NewDepots.FleetManagementPresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Scope("prototype")
public class AddDepotDialog extends VerticalLayout {

    private FleetManagementPresenter presenter;

    private TextField depotNameField;

    private TextField depotCodeField;

    private TextArea depotAddress;

    private NumberField fleetCapacity;

    private NumberField cngBuses;

    private NumberField dieselBuses;

    private NumberField electricBuses;

    private NumberField fleetSize;

    private Checkbox cngRefuellingStation;

    private Checkbox dieselRefuellingStation;

    private Checkbox chargingStation;

    private ComboBox<CompanyDto> companyDtoComboBox;

    private Button saveButton;
    private Button cancelButton;
    private MultiSelectComboBox<BusType> busTypeMultiSelectComboBox;
    private H3 title;
    private Binder<DepotDto> binder;
    private List<BusTypeVO> busTypeVOS = new ArrayList<>();
    public AddDepotDialog() {

        setDimensions();
        initializeFields();
        initializeBinder();
        initializeButtons();
        add(mainLayout());
    }

    private void setDimensions() {
        setSizeFull();
    }

    private void initializeFields() {
        depotNameField = createTextField("Depot Name");
        depotCodeField = createTextField("Depot Code");
        depotAddress = createTextArea("Depot Address");
        fleetCapacity = createNumberField("Fleet Capacity");
        fleetCapacity.setReadOnly(true);
        fleetSize = createNumberField("Fleet Size");
        fleetSize.setReadOnly(true);
        cngRefuellingStation = createCheckbox("CNG Refuelling Station");
        dieselRefuellingStation = createCheckbox("Diesel Refuelling Station");
        chargingStation = createCheckbox("Charging Station");
        companyDtoComboBox = new ComboBox<>("Company"/*, presenter.getCompanies()*/);
        busTypeMultiSelectComboBox = new MultiSelectComboBox<>("Bus Types"/*, presenter.getBusTypes()*/);
        companyDtoComboBox.setItemLabelGenerator(CompanyDto::getCompanyName);
        busTypeMultiSelectComboBox.setItemLabelGenerator(BusType::getModelName);
        title = new H3("Add New Depot");
        cngBuses = createNumberField("CNG Buses");
        dieselBuses = createNumberField("Diesel Buses");
        electricBuses = createNumberField("Electric Buses");
    }

    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(depotNameField).bind(DepotDto::getDepotName,DepotDto::setDepotName);
        binder.forField(depotCodeField).bind(DepotDto::getDepotCode,DepotDto::setDepotCode);
        binder.forField(depotAddress).bind(DepotDto::getDepotLocation,DepotDto::setDepotLocation);
        binder.forField(fleetCapacity).bind(value->{

            return Objects.isNull(value.getFleetCapacity()) ? 0D: value.getFleetCapacity();
        },(dto,value) -> dto.setFleetCapacity(fleetCapacity.getValue()));
        binder.forField(chargingStation).bind(DepotDto::getChargingStation,DepotDto::setChargingStation);
        binder.forField(dieselRefuellingStation).bind(DepotDto::getDieselRefuelling,DepotDto::setDieselRefuelling);
        binder.forField(cngRefuellingStation).bind(DepotDto::getCngRefuelling,DepotDto::setCngRefuelling);
        binder.forField(companyDtoComboBox).bind(value -> {
            return companyDtoComboBox.getDataProvider().fetch(new Query<>()).toList().stream()
                    .filter(company -> company.getId().equals(value.getCompanyId())).findFirst().orElse(null);
        }, (dto,value) -> dto.setCompanyId(value.getId()));
        /*binder.forField(busTypeMultiSelectComboBox).bind(value -> {
            return CollectionUtils.isEmpty(value.getBusTypes()) ? new HashSet<>() : value.getBusTypes().stream().map(id -> presenter.getAllTypes().get(id)).collect(Collectors.toSet());
        }, (dto,value) -> dto.setBusTypes(value.stream().map(BusType::getBusTypeId).collect(Collectors.toSet())));*/
        binder.setBean(new DepotDto());
        binder.forField(fleetSize).bind(DepotDto::getFleetSize,DepotDto::setFleetSize);
        binder.forField(cngBuses).bind(DepotDto::getCngBuses, (dto, value) ->{
            dto.setFleetSize(cngBuses.getValue() + fleetSize.getValue());
            dto.setCngBuses(value);
            binder.refreshFields();
        });
        binder.forField(dieselBuses).bind(DepotDto::getDieselBuses, (dto, value) ->{
            dto.setFleetSize(dieselBuses.getValue() + fleetSize.getValue());
            dto.setDieselBuses(value);
            binder.refreshFields();
        });
        binder.forField(electricBuses).bind(DepotDto::getElectricBuses,(dto, value) ->{
            dto.setFleetSize(electricBuses.getValue() + fleetSize.getValue());
            dto.setElectricBuses(value);
            binder.refreshFields();
        });

    }

    private TextField createTextField(String label) {
        return new TextField(label);
    }

    private TextArea createTextArea(String label) {
        return new TextArea(label);
    }

    private NumberField createNumberField(String label) {
        return new NumberField(label);
    }

    private Checkbox createCheckbox(String label) {
        return new Checkbox(label);
    }

    private <T> ComboBox<T> createComboBox(String label, List<T> items){
        return new ComboBox<>(label);
    }

    private VerticalLayout mainLayout(){
        VerticalLayout layout = new VerticalLayout(
                createGridAndFieldLayout(),
                createRowLayout(saveButton,cancelButton)
        );
        layout.setPadding(true);
        layout.setSpacing(true);
        layout.setWidth("100%");
        layout.setHeight("100%");
//        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        return layout;
    }
    private void initializeButtons(){
        saveButton = createButton("Save");
/*        saveButton.addClickListener(buttonClickEvent -> {
            presenter.onSave(binder.getBean());
            close();
        });*/
        cancelButton = createButton("Cancel");

    }

    private Button createButton(String label){
        return new Button(label);
    }

    public void setBeanInBinder(DepotDto dto){
        binder.setBean(dto);
    }

    public HorizontalLayout createRowLayout(Component... components){
        return new HorizontalLayout(components);
    }

    public VerticalLayout createFieldsLayout(){
        return new VerticalLayout(createRowLayout(title),
                createRowLayout(depotNameField,depotCodeField),
                createRowLayout(depotAddress),
                createRowLayout(fleetCapacity, fleetSize),
                createRowLayout(cngBuses, electricBuses, dieselBuses),
                createRowLayout(cngRefuellingStation,dieselRefuellingStation,chargingStation),
                createRowLayout(companyDtoComboBox,busTypeMultiSelectComboBox));
    }

    public HorizontalLayout createGridAndFieldLayout(){
        Grid<BusTypeVO> grid = new Grid<>();

        grid.addColumn(new ComponentRenderer<>(event ->{
            ComboBox<BusType> cmb = new ComboBox<BusType>();
          /*  cmb.setItems(presenter.getBusTypes());*/
            cmb.setItemLabelGenerator(BusType::getModelName);
            return cmb;
        })).setHeader("Select a model");

        grid.addColumn(new ComponentRenderer<>(event ->{
            NumberField noOfBuses = new NumberField();
            noOfBuses.setValue(event.getNoOfBuses() == null? 0d : event.getNoOfBuses());
            noOfBuses.addValueChangeListener(onValueChange ->{
                Double oldValue = binder.getBean().getFleetSize();
                binder.getBean().setFleetSize(onValueChange.getValue() + oldValue);
                binder.refreshFields();
            });
            return noOfBuses;
        })).setHeader("No Of Buses");

        grid.setItems(busTypeVOS);
        HorizontalLayout layout = new HorizontalLayout(createFieldsLayout());
        layout.setWidth("100%");
        layout.setHeight("100%");
        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addClickListener(event -> {
            busTypeVOS.add(new BusTypeVO());
            grid.getDataProvider().refreshAll();
        });
        VerticalLayout gridLayout = new VerticalLayout(addButton,grid);
        gridLayout.setHeight("100%");
        gridLayout.setWidth("100%");
        gridLayout.setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout(createFieldsLayout(),gridLayout);
        horizontalLayout.setHeight("100%");
        horizontalLayout.setWidth("100%");
        return horizontalLayout;
    }
}
