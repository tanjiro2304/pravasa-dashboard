package com.example.application.views.NewRoutes.components;

import com.example.application.dto.HaltDto;
import com.example.application.views.NewRoutes.RouteManagementPresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.ArrayList;

public class AddNewRouteDialog extends Dialog {
    private RouteManagementPresenter routeManagementPresenter;
    private TextField routeNo;
    private TextField source;
    private TextField destination;
    private NumberField fare;
    private NumberField distance;
    private Checkbox isAirConditioned;
    private RadioButtonGroup<Long> routeType;
    private Grid<HaltDto> grid;
    private Button save;
    private Button cancel;
    public AddNewRouteDialog(RouteManagementPresenter routeManagementPresenter) {
        this.routeManagementPresenter = routeManagementPresenter;
        setDimensions();
        initializeFields();
        initializeGrid();
        addFieldsInLayout();
    }

    private void setDimensions() {
        setHeaderTitle("Add New Route");
        setHeight("80%");

    }

    private void initializeGrid() {
        grid = new Grid<>();
        grid.addColumn(HaltDto::getPosition)
                .setHeader("Position") // Header set directly
                .setKey("position")
                .setAutoWidth(true);

        // Stop Name column with ComboBox (editable)
        grid.addColumn(new ComponentRenderer<>(halt -> {
                    ComboBox<String> stopNameComboBox = new ComboBox<>();
                    stopNameComboBox.setItems(new ArrayList<>()); // Set available stops
                    stopNameComboBox.setValue(halt.getStopName()); // Set current value
                    stopNameComboBox.addValueChangeListener(event -> halt.setStopName(event.getValue())); // Update HaltDto

                    return stopNameComboBox;
                })).setHeader("Stop Name") // Header set directly
                .setKey("stopName")
                .setAutoWidth(true);

        // Set grid properties
        grid.setSizeFull();
        grid.setColumnReorderingAllowed(true);
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
        VerticalLayout mainLayout = new VerticalLayout(createRow(routeNo, source, destination),
                createRow(fare, distance), createRow(isAirConditioned),createRow(routeType),createRow(new Button(VaadinIcon.PLUS.create())),grid, createRow(save,cancel));
        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(mainLayout);
    }

    private HorizontalLayout createRow(Component... components){
        return new HorizontalLayout(components);
    }


}
