package com.example.application.views.NewRoutes;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.RouteDto;
import com.example.application.services.CompanyService;
import com.example.application.views.NewRoutes.components.AddNewRouteDialog;
import com.example.application.views.NewRoutes.components.AddNewStopDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Add New Route")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@UIScope
@SpringComponent
public class RouteManagementView extends VerticalLayout {

    private Binder<RouteDto> binder;
    private Grid<RouteDto> grid;
    private Button addNewRoute;
    private Button addNewBusStop;
    private ComboBox<CompanyDto> comboBox;
    private final RouteManagementPresenter routeManagementPresenter;
    public RouteManagementView(RouteManagementPresenter presenter) {
        this.routeManagementPresenter = presenter;
        initializeGrid();
        initializeFields();
        add(initializeMenuBar(), comboBox,grid);
    }

    private HorizontalLayout initializeMenuBar() {
        addNewRoute = new Button("Add New Route");
        addNewRoute.addClickListener(event -> {
            AddNewRouteDialog dialog = new AddNewRouteDialog(routeManagementPresenter);
            dialog.open();
        });

        addNewBusStop = new Button("Add New Bus Stop");
        addNewBusStop.addClickListener(event ->{
            AddNewStopDialog addNewStopDialog= new AddNewStopDialog(routeManagementPresenter);
            addNewStopDialog.open();
        });
        return new HorizontalLayout(addNewRoute,addNewBusStop);
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

//        grid.addColumn(RouteDto::getIsAirConditioned)
//                .setHeader("Air Conditioned")
//                .setKey("isAirConditioned")
//                .setAutoWidth(true)
//                .setRenderer(route -> route.getIsAirConditioned() ? "Yes" : "No"); // Convert Boolean to Yes/No

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
        grid.setSizeFull();
        grid.setColumnReorderingAllowed(true);

        // Adding a header row with alternate names

    }

    private void initializeFields(){
        comboBox = new ComboBox<>("Select a Transport Undertaking");
        comboBox.setItems(routeManagementPresenter.getCompanies());
        comboBox.setItemLabelGenerator(CompanyDto::getCompanyName);
    }

}
