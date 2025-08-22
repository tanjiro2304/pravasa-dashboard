package info.pravasa.application.views.dashboard.roadTransport.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import info.pravasa.application.services.RouteService;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.RouteDto;

import java.util.function.Consumer;

public class AddRouteDialog extends Dialog {

    private TextField routeNoField;

    private TextField sourceField;

    private TextField destinationField;

    private NumberField distanceField;

    private Consumer<RouteDto> routeDtoConsumer;

    private Binder<RouteDto> binder;
    private DepotDto depotDto;
    private Button submitButton;


    public AddRouteDialog(Consumer<RouteDto> routeDtoConsumer, DepotDto depotDto){
        this.routeDtoConsumer = routeDtoConsumer;
        this.depotDto = depotDto;
        initializeFields();
        VerticalLayout verticalLayout = new VerticalLayout(routeNoField, sourceField, destinationField, distanceField, submitButton);
        verticalLayout.setSizeFull();
        setSizeFull();
        setHeight("45rem");
        setWidth("20rem");
        add(verticalLayout);
    }

    private void initializeFields() {
        routeNoField = new TextField("Route No");
        sourceField = new TextField("Source");
        destinationField = new TextField("Destination");
        distanceField = new NumberField("Distance (in km)");

        routeNoField.setRequiredIndicatorVisible(true);
        sourceField.setRequiredIndicatorVisible(true);
        destinationField.setRequiredIndicatorVisible(true);
        distanceField.setRequiredIndicatorVisible(true);
        submitButton = new Button("Submit", event -> {
            RouteDto routeDto = new RouteDto();
            if (binder.writeBeanIfValid(routeDto)) {
                routeDto.setDepotDto(depotDto);
                routeDtoConsumer.accept(routeDto);
                close();
            }
        });
        initializeBinder();
    }

    private void initializeBinder() {
        binder = new Binder<>(RouteDto.class);
        binder.forField(routeNoField)
                .asRequired("Route No is required")
                .bind(RouteDto::getRouteNo, RouteDto::setRouteNo);

        binder.forField(sourceField)
                .asRequired("Source is required")
                .bind(RouteDto::getSource, RouteDto::setSource);

        binder.forField(destinationField)
                .asRequired("Destination is required")
                .bind(RouteDto::getDestination, RouteDto::setDestination);

        binder.forField(distanceField)
                .asRequired("Distance is required")
                .bind(RouteDto::getDistance, RouteDto::setDistance);
    }
}
