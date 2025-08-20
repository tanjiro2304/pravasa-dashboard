package info.pravasa.application.views.dashboard.roadTransport.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.application.services.BusModelService;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.dto.BusModelDto;
import info.pravasa.dto.enums.FuelType;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AddBusModel extends Dialog {
    private VerticalLayout mainLayout;

    private TextField modelNameField;

    private TextField modelCode;

    private TextField manufacturerField;

    private NumberField seatingCapacityField;

    private ComboBox<FuelType> fuelTypeComboBox;

    private NumberField mileageField;

    private NumberField tankCapacityField;

    private NumberField batteryCapacityField;

    private Binder<BusModelDto> binder;

    private BusModelService busModelService;

    private Consumer<BusModelDto> busModelDtoConsumer;

    private Supplier<List<BusModelDto>> busModelDtoSupplier;

    private Grid<BusModelDto> busModelDtoGrid;

    private Button submitButton;
    public AddBusModel(Consumer<BusModelDto> busModelDtoConsumer, Supplier<List<BusModelDto>> busModelDtoSupplier) {
        this.busModelDtoConsumer = busModelDtoConsumer;
        this.busModelDtoSupplier = busModelDtoSupplier;
        mainLayout = new VerticalLayout();
        setHeight("50rem");
        setWidth("50rem");
        initializeComponents();
        initializeBinder();
        initializeGrid();
        add(mainLayout, busModelDtoGrid);
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(modelNameField)
                .asRequired("Model Name is required")
                .bind(BusModelDto::getModelName, BusModelDto::setModelName);

        binder.forField(modelCode)
                .asRequired("Model Code is required")
                .bind(BusModelDto::getModelCode, BusModelDto::setModelCode);

        binder.forField(manufacturerField)
                .asRequired("Manufacturer is required")
                .bind(BusModelDto::getManufacturer, BusModelDto::setManufacturer);

        binder.forField(seatingCapacityField)
                .asRequired("Seating Capacity is required")
                .bind(BusModelDto::getSeatingCapacity, BusModelDto::setSeatingCapacity);

        binder.forField(fuelTypeComboBox)
                .asRequired("Fuel Type is required")
                .bind(BusModelDto::getFuelType, BusModelDto::setFuelType);

        binder.forField(mileageField)
                .asRequired("Mileage is required")
                .bind(BusModelDto::getMileage, BusModelDto::setMileage);

        binder.forField(tankCapacityField)
                .bind(BusModelDto::getTankCapacity, BusModelDto::setTankCapacity);

        binder.forField(batteryCapacityField)
                .bind(BusModelDto::getBatteryCapacity, BusModelDto::setBatteryCapacity);
    }

    private void initializeComponents() {
        modelNameField = CommonComponent.createTextField("Model Name", true, true);
        modelCode = CommonComponent.createTextField("Model Code", true, true);
        manufacturerField = CommonComponent.createTextField("Manufacturer", true, true);
        seatingCapacityField = CommonComponent.createNumberField("Seating Capacity", true, true);
        fuelTypeComboBox = CommonComponent.createComboBox("Fuel Type",true,true, List.of(FuelType.values()));
        mileageField = CommonComponent.createNumberField("Mileage (km/l)", true, true);
        tankCapacityField = CommonComponent.createNumberField("Tank Capacity", true, true);
        batteryCapacityField = CommonComponent.createNumberField("Battery Capacity (kWh)", true, true);
        submitButton = new Button("Submit");
        submitButton.addClickListener(event -> {
            if (!binder.isValid()) {
                binder.validate();
                return;
            }
            BusModelDto busModelDto = new BusModelDto();
            try {
                binder.writeBean(busModelDto);
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            busModelDtoConsumer.accept(busModelDto);
            Notification.show("Data Inserted Successfully", 3000, Notification.Position.MIDDLE);
            binder.readBean(new BusModelDto());
            refreshGrid();
        });
        mainLayout.add(
                CommonComponent.createRow(modelNameField, modelCode),
                CommonComponent.createRow(manufacturerField, seatingCapacityField),
                CommonComponent.createRow(fuelTypeComboBox, mileageField),
               CommonComponent.createRow(tankCapacityField, batteryCapacityField),
                submitButton
        );

    }

    private void refreshGrid() {
        busModelDtoGrid.setItems(busModelDtoSupplier.get());
    }

    public void initializeGrid(){
        busModelDtoGrid = new Grid<>();
        busModelDtoGrid.addColumn(BusModelDto::getModelName).setHeader("Model Name");
        busModelDtoGrid.addColumn(BusModelDto::getModelCode).setHeader("Model Code");
        busModelDtoGrid.addColumn(BusModelDto::getManufacturer).setHeader("Manufacturer");
        busModelDtoGrid.addColumn(BusModelDto::getSeatingCapacity).setHeader("Seating Capacity");
        busModelDtoGrid.addColumn(BusModelDto::getFuelType).setHeader("Fuel Type");
        busModelDtoGrid.addColumn(BusModelDto::getMileage).setHeader("Mileage (km/l)/ Range On Full Charge (km)");
        busModelDtoGrid.addColumn(BusModelDto::getTankCapacity).setHeader("Tank Capacity (L)");
        busModelDtoGrid.addColumn(BusModelDto::getBatteryCapacity).setHeader("Battery Capacity (kWh)");
        busModelDtoGrid.setItems(busModelDtoSupplier.get());
    }
}
