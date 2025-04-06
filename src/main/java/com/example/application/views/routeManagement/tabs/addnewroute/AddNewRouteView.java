package com.example.application.views.routeManagement.tabs.addnewroute;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.HaltDto;
import com.example.application.dto.RouteDto;
import com.example.application.views.routeManagement.RouteManagementPresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.ArrayList;
import java.util.Objects;

public class AddNewRouteView extends VerticalLayout {
    private final AddNewRoutePresenter addNewRoutePresenter;
    private TextField routeNo;
    private TextField source;
    private TextField destination;
    private NumberField fare;
    private NumberField distance;
    private Checkbox isAirConditioned;
    private RadioButtonGroup<Long> routeType;
    private Grid<RouteDto> grid;
    private ComboBox<CompanyDto> comboBox;
    private Button save;
    private Button cancel;
    private TabSheet tabSheet;
    private VerticalLayout mainLayout;
    public AddNewRouteView(AddNewRoutePresenter addNewRoutePresenter) {
        this.addNewRoutePresenter = addNewRoutePresenter;
        initializeFields();
        initializeComboBOx();
        initializeGrid();
        addFieldsInLayout();
        initializeTabSheet();
        add(tabSheet);

    }

    private void initializeTabSheet() {
        tabSheet = new TabSheet();
        tabSheet.add("Add Route", mainLayout);
        VerticalLayout verticalLayout = new VerticalLayout(comboBox, grid);
        verticalLayout.setSizeFull();
        tabSheet.add("Route List", verticalLayout);
        tabSheet.setSizeFull();
    }

    private void initializeFields() {
        routeNo = new TextField("Route No");
        source = new TextField("Source");
        destination = new TextField("Destination");
        fare = new NumberField("Fare");
        distance = new NumberField("Distance");
        isAirConditioned = new Checkbox("AC");

        // Initializing radio button group with route types
        routeType = new RadioButtonGroup<>();
        routeType.setLabel("Route Type");
        routeType.setItems(1L, 2L, 3L);
        routeType.setItemLabelGenerator(type -> {
            return switch (type.intValue()) {
                case 1 -> "Ordinary";
                case 2 -> "Limited";
                case 3 -> "Express";
                default -> "Unknown";
            };
        });
        save =  new Button("Save");
        cancel = new Button("Cancel");
    }

    private void addFieldsInLayout() {
        mainLayout = new VerticalLayout(createRow(routeNo, source, destination),
                createRow(fare, distance), createRow(isAirConditioned),createRow(routeType),createRow(new Button(VaadinIcon.PLUS.create())),createRow(save,cancel));
        mainLayout.setSizeFull();
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    private HorizontalLayout createRow(Component... components){
        return new HorizontalLayout(components);
    }

    private void initializeGrid() {
        grid = new Grid<>();
        grid.addColumn(RouteDto::getRouteId)
                .setHeader("Route ID")
                .setKey("routeId")
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getRouteNo)
                .setHeader("Route No")
                .setKey("routeNo")
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getSource)
                .setHeader("Source")
                .setKey("source")
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getDestination)
                .setHeader("Destination")
                .setKey("destination")
                .setAutoWidth(true);


        grid.addColumn(RouteDto::getRouteType)
                .setHeader("Route Type")
                .setKey("routeType")
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getDepotName)
                .setHeader("Depot Name")
                .setKey("depotName")
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getFare)
                .setHeader("Fare (₹)")
                .setKey("fare")
                .setAutoWidth(true);

        grid.addColumn(RouteDto::getDistance)
                .setHeader("Distance (km)")
                .setKey("distance")
                .setAutoWidth(true);

        // Set grid properties
        grid.setHeightFull();
        grid.setColumnReorderingAllowed(true);

        // Adding a header row with alternate names

    }

    private void initializeComboBOx(){
        comboBox = new ComboBox<>("Select a Transport Undertaking");
        comboBox.setItems(addNewRoutePresenter.getCompanies());
        comboBox.setWidthFull();
        comboBox.setItemLabelGenerator(CompanyDto::getCompanyName);
        comboBox.addValueChangeListener(value -> {
            if (Objects.nonNull(value) && Objects.nonNull(value.getValue().getId())){
                grid.setItems(addNewRoutePresenter.findByCompanyId(value.getValue().getId()));
            } else{
                Notification.show("No Data For Selected Company", 2500, Notification.Position.MIDDLE);
            }
        });
    }


}
