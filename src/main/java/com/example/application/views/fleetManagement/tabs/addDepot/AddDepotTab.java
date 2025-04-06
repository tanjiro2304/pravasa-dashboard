package com.example.application.views.fleetManagement.tabs.addDepot;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.utils.Icons;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddDepotTab extends VerticalLayout {

    private Grid<DepotDto> depotDtoGrid;
    private final ComboBox<CompanyDto> companyDtoComboBox;
    private final AddDepotPresenter addDepotPresenter;
    private HorizontalLayout btnLayout;
    private final TabSheet tabSheet;

    public AddDepotTab(AddDepotPresenter addDepotPresenter) {
        this.addDepotPresenter = addDepotPresenter;
        this.tabSheet = new TabSheet();
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
        tabSheet.setSizeFull();
        add(tabSheet);
    }

    private void initializeButtonLayout() {
        Button submitButton = new Button("Submit");
        btnLayout = new HorizontalLayout(submitButton);
        submitButton.addClickListener(event -> {
            if(Objects.nonNull(companyDtoComboBox.getValue())) {
                setDataInGrid(companyDtoComboBox.getValue().getId());
            }
            else{
                Notification.show("Please Select a Comapny",5000, Notification.Position.MIDDLE);
                depotDtoGrid.setItems(new ArrayList<>());
            }
        });
    }

    private void initializeGrid() {
        depotDtoGrid = new Grid<>();
        initializeColumns();
        initializeGridListeners();

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

    public void setDataInGrid(Long id){
        List<DepotDto> depots = addDepotPresenter.findDepotByCompanyId(id);
        if(CollectionUtils.isEmpty(depots)){
            Notification.show("No Data for this company",5000, Notification.Position.MIDDLE);
        }else{
            depotDtoGrid.setItems(depots);
            depotDtoGrid.getDataProvider().refreshAll();
        }

    }
}
