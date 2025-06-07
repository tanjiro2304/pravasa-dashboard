package com.example.application.views.routeManagement;

import com.example.application.dto.RouteDto;
import com.example.application.views.routeManagement.tabs.addnewroute.AddNewRoutePresenter;
import com.example.application.views.routeManagement.tabs.addnewroute.AddNewRouteView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Route Management")
@Route("route-management")
@Menu(order = 0, icon = LineAwesomeIconUrl.GLOBE_SOLID)
@UIScope
@SpringComponent
@Secured("ADMIN")
public class RouteManagementView extends VerticalLayout {


    private ApplicationContext applicationContext;
    private Binder<RouteDto> binder;
    private Grid<RouteDto> grid;
    private Button addNewRoute;
    private Button addNewBusStop;
    private final RouteManagementPresenter routeManagementPresenter;
    private TabSheet tabSheet;

    public RouteManagementView(ApplicationContext applicationContext, RouteManagementPresenter presenter) {
        this.applicationContext = applicationContext;
        this.routeManagementPresenter = presenter;

        initializeTabSheet();
        setSizeFull();
        add(tabSheet);
    }

    private void initializeTabSheet() {
        tabSheet = new TabSheet();
        AddNewRouteView addNewRouteView = new AddNewRouteView(applicationContext.getBean(AddNewRoutePresenter.class));
        addNewRouteView.setSizeFull();
        tabSheet.add("Routes", addNewRouteView);
        tabSheet.setSizeFull();
    }





}
