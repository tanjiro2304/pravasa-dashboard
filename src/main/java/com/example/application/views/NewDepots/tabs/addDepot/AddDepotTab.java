package com.example.application.views.NewDepots.tabs.addDepot;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.utils.Icons;
import com.example.application.views.components.AddBusType;
import com.example.application.views.components.AddCompanyDialog;
import com.example.application.views.components.AddDepotDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;

import java.util.Objects;

public class AddDepotTab extends VerticalLayout {

    private Grid<DepotDto> depotDtoGrid;
    private final ComboBox<CompanyDto> companyDtoComboBox;
    private final AddDepotPresenter addDepotPresenter;
    private HorizontalLayout btnLayout;
    private TabSheet tabSheet;

    public AddDepotTab(AddDepotPresenter addDepotPresenter) {
        this.addDepotPresenter = addDepotPresenter;
        tabSheet = new TabSheet();
        companyDtoComboBox = new ComboBox<>("Select a Company", addDepotPresenter.getCompanies());
        companyDtoComboBox.setItemLabelGenerator(CompanyDto::getCompanyName);

        initializeLayout();
    }

    private void initializeLayout() {
        VerticalLayout mainLayout = new VerticalLayout();
        initializeGrid();
        initializeButtonLayout();
        setSizeFull();
        tabSheet.add("List Of Depots", new VerticalLayout(companyDtoComboBox,btnLayout,depotDtoGrid));
        tabSheet.add("Add New Depot", new AddDepotDialog());
        tabSheet.setSizeFull();
        add(tabSheet);
    }

    private void initializeButtonLayout() {
        Button submitButton = new Button("Submit");
        btnLayout = new HorizontalLayout(submitButton);
    }

    private void initializeGrid() {
        depotDtoGrid = new Grid<>();
        initializeColumns();
        initializeGridListeners();
        setDataInGrid();
    }

    private void initializeColumns() {
        int serialNo = 0;
        depotDtoGrid.addColumn(data -> serialNo + 1);
        depotDtoGrid.addColumn(DepotDto::getDepotName)
                .setKey("DEPOT")
                .setHeader("Depot")
                .setSortable(true);
        depotDtoGrid.addColumn(DepotDto::getDepotCode)
                .setKey("DEPOT_CODE")
                .setHeader("Depot Code")
                .setSortable(true);
        depotDtoGrid.addColumn(DepotDto::getDepotLocation)
                .setKey("ADDRESS")
                .setHeader("Address")
                .setSortable(true);
        depotDtoGrid.addColumn(DepotDto::getFleetCapacity)
                .setKey("FLEET_CAPACITY")
                .setHeader("Fleet Capacity")
                .setSortable(true);
        depotDtoGrid.addColumn(DepotDto::getFleetSize)
                .setKey("FLEET_SIZE")
                .setHeader("Fleet Size")
                .setSortable(true);
        depotDtoGrid.addComponentColumn(dto -> {
                    if(!Objects.isNull(dto.getCngRefuelling()) && dto.getCngRefuelling()){
                        return Icons.getIcon("CNG_REFUELLING");
                    }
                    return new Span();
                })
                .setKey("CNG_REFUELLING")
                .setHeader("Cng Refuelling")
                .setSortable(true);

        depotDtoGrid.addComponentColumn(dto -> {
                    if(!Objects.isNull(dto.getDieselRefuelling()) && dto.getDieselRefuelling()){
                        return Icons.getIcon("DIESEL_REFUELLING");
                    }
                    return new Span();
                })
                .setKey("DIESEL_REFUELLING")
                .setHeader("Diesel Refuelling")
                .setSortable(true);

        depotDtoGrid.addComponentColumn(dto ->{
                    if(!Objects.isNull(dto.getChargingStation()) && dto.getChargingStation()){
                        return Icons.getIcon("CHARGING_STATION");
                    }
                    return new Span();
                })
                .setKey("CHARGING_STATION")
                .setHeader("Charging Station")
                .setSortable(true);
    }

    private void initializeGridListeners(){
//        depotDtoGrid.addItemDoubleClickListener(event -> {
//            AddDepotDialog dialogue = new AddDepotDialog(addDepotPresenter);
//            dialogue.setBeanInBinder(event.getItem());
//            dialogue.open();
//        });
    }

    public void setDataInGrid(){
        depotDtoGrid.setItems(addDepotPresenter.getDataForGrid());
        depotDtoGrid.getDataProvider().refreshAll();
    }
}
