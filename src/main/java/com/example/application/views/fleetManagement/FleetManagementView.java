package com.example.application.views.fleetManagement;

import com.example.application.views.fleetManagement.tabs.addCompany.AddCompanyPresenter;
import com.example.application.views.fleetManagement.tabs.addCompany.AddCompanyTab;
import com.example.application.views.fleetManagement.tabs.addDepot.AddDepotPresenter;
import com.example.application.views.fleetManagement.tabs.addDepot.AddDepotTab;
import com.example.application.views.fleetManagement.tabs.addNewModels.AddNewModelsPresenter;
import com.example.application.views.fleetManagement.tabs.addNewModels.AddNewModelsTab;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.context.ApplicationContext;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Fleet Management")
@Route("fleet-management")
@Menu(order = 1, icon = LineAwesomeIconUrl.FILE)
@SpringComponent
@UIScope
public class FleetManagementView extends VerticalLayout {


    ApplicationContext applicationContext;
    private final TabSheet tabs;


    public FleetManagementView(FleetManagementPresenter presenter, ApplicationContext applicationContext) {
        tabs = new TabSheet();
        this.applicationContext = applicationContext;
        initializeTabLayout();
    }

    private void initializeTabLayout() {
        AddCompanyTab addCompanyTab = new AddCompanyTab(applicationContext.getBean(AddCompanyPresenter.class));
        AddDepotTab addDepotTab = new AddDepotTab(applicationContext.getBean(AddDepotPresenter.class));
        AddNewModelsTab addNewModelsTab = new AddNewModelsTab(applicationContext.getBean(AddNewModelsPresenter.class));
        tabs.add("Companies", addCompanyTab);
        tabs.add("Depot Management", addDepotTab);
        tabs.add("Bus Models", addNewModelsTab);
        tabs.setSizeFull();
        add(tabs);
    }


}

