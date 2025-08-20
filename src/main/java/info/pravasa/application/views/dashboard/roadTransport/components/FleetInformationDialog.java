package info.pravasa.application.views.dashboard.roadTransport.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import info.pravasa.dto.*;

import java.util.ArrayList;
import java.util.List;


public class FleetInformationDialog extends Dialog {

    private ComboBox<Company> companyComboBox;

    private NumberField fleetSizeField;

    private Grid<FleetInformationDto> fleetInformationDtoGrid;

    private Grid<BusModelDistributionDto> distributionDtoGrid;

    private VerticalLayout fleetInfoLayout;

    private VerticalLayout distributionLayout;
    private DepotDto selectedDepot;
    private List<FleetInformationDto> fleetInformationDtoList = new ArrayList<>();
    private List<BusModelDistributionDto> busModelDistributionDtoList = new ArrayList<>();


    public FleetInformationDialog(DepotDto selectedDepot) {

        this.selectedDepot = selectedDepot;
        setWidth("50rem");
        setHeight("50rem");
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        setDraggable(true);
        setResizable(true);
        setModal(true);
        initializeFleetInfoGrid();
        initializeDistributionGrid();
        HorizontalLayout mainLayout = new HorizontalLayout(fleetInfoLayout,distributionLayout);
        mainLayout.setSizeFull();
        add(new Span("Fleet Information"),mainLayout);
    }

    private void initializeFleetInfoGrid() {
        fleetInformationDtoGrid = new Grid<>();
        fleetInformationDtoGrid.setSizeFull();
        fleetInformationDtoGrid.addColumn(fleetInfo -> fleetInfo.getContractorDto().getCompany().getCompanyName()).setHeader("Company");
        fleetInformationDtoGrid.addColumn(FleetInformationDto::getFleetSize).setHeader("Fleet Size");
        fleetInformationDtoGrid.setItems(fleetInformationDtoList);
        Button addFleetButton = new Button("Add", event -> {
            fleetInformationDtoList.add(new FleetInformationDto());
            fleetInformationDtoGrid.getDataProvider().refreshAll();
        });

        fleetInfoLayout = new VerticalLayout(addFleetButton,fleetInformationDtoGrid);
    }

    private void initializeDistributionGrid() {
        distributionDtoGrid = new Grid<>();
        distributionDtoGrid.addColumn(BusModelDistributionDto::getBusModelDto).setHeader("Bus Model");
        distributionDtoGrid.addColumn(BusModelDistributionDto::getTotalBuses).setHeader("Count");
        distributionDtoGrid.setSizeFull();
        distributionDtoGrid.setItems(busModelDistributionDtoList);
        Button addDistributionButton = new Button("Add");
        distributionLayout = new VerticalLayout(addDistributionButton, distributionDtoGrid);
    }

}
