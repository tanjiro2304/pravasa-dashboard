package info.pravasa.application.views.dashboard.roadTransport.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.dto.Company;
import info.pravasa.dto.StopDto;
import jakarta.annotation.PostConstruct;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@UIScope
@SpringComponent
public class StopTab extends VerticalLayout {
    @Setter
    private Company selectedCompany;
    private TextField stopNameField;
    private NumberField latitudeField;
    private NumberField longitudeField;
    private Binder<StopDto> binder;
    private Grid<StopDto> grid;
    private Button submitButton;
    private StopDto selectedStopDto;
    @Setter
    private Consumer<StopDto> stopDtoConsumer;
    @Setter
    private Function<Long,List<StopDto>> listConsumer;

    @PostConstruct
    private void init() {
        stopNameField = CommonComponent.createTextField("Stop Name", true, true);
        latitudeField = CommonComponent.createNumberField("Latitude", true, true);
        submitButton = new Button("Submit", event -> {
            if(Objects.isNull(selectedStopDto)){
                selectedStopDto = new StopDto();
                selectedStopDto.setCompanyId(selectedCompany.getId());
            } else {
                // If a stop is selected, update the existing stop

            }
            if (binder.writeBeanIfValid(selectedStopDto)) {
                refreshGrid();
                stopDtoConsumer.accept(selectedStopDto);
                Notification.show("Stop added successfully!", 3000, Notification.Position.MIDDLE);
                binder.readBean(new StopDto()); // Reset the form
            } else {
                Notification.show("Please fill in all required fields correctly.", 3000, Notification.Position.MIDDLE);
            }
        });
        longitudeField = CommonComponent.createNumberField("Longitude", true, true);
        initializeBinder();
        initializeGrid();
        VerticalLayout verticalLayout = new VerticalLayout(stopNameField, latitudeField, longitudeField, submitButton);
        verticalLayout.setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout(verticalLayout, grid);
        horizontalLayout.setSizeFull();

        add(horizontalLayout);
        setSizeFull();
    }

    private void initializeGrid() {
        grid = new Grid<>();
        grid.addColumn(StopDto::getStopName).setHeader("Stop Name");
        grid.addColumn(StopDto::getLatitude).setHeader("Latitude");
        grid.addColumn(StopDto::getLongitude).setHeader("Longitude");
        grid.setSizeFull();
        grid.addItemClickListener(event -> {
            selectedStopDto = event.getItem();
            binder.readBean(selectedStopDto);
        });
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(stopNameField)
                .asRequired("Stop name is required")
                .bind(StopDto::getStopName, StopDto::setStopName);
        binder.forField(latitudeField)
                .asRequired("Latitude is required")
                .bind(StopDto::getLatitude, StopDto::setLatitude);
        binder.forField(longitudeField)
                .asRequired("Longitude is required")
                .bind(StopDto::getLongitude, StopDto::setLongitude);

        binder.readBean(new StopDto());

    }

    public void refreshGrid() {
        List<StopDto> stopDtos = listConsumer.apply(selectedCompany.getId());
        grid.setItems(stopDtos);
    }
}
