package com.example.application.views.NewDepots;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.DepotDto;
import com.example.application.utils.Icons;
import com.example.application.views.NewDepots.tabs.addCompany.AddCompanyPresenter;
import com.example.application.views.NewDepots.tabs.addCompany.AddCompanyTab;
import com.example.application.views.NewDepots.tabs.addDepot.AddDepotPresenter;
import com.example.application.views.NewDepots.tabs.addDepot.AddDepotTab;
import com.example.application.views.NewDepots.tabs.addNewModels.AddNewModelsPresenter;
import com.example.application.views.NewDepots.tabs.addNewModels.AddNewModelsTab;
import com.example.application.views.components.AddBusType;
import com.example.application.views.components.AddCompanyDialog;
import com.example.application.views.components.AddDepotDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Objects;

@PageTitle("Fleet Management")
@Route("fleet-management")
@Menu(order = 1, icon = LineAwesomeIconUrl.FILE)
@SpringComponent
@UIScope
public class FleetManagementView extends VerticalLayout {


    ApplicationContext applicationContext;
    private final TabSheet tabs;
    private AddCompanyTab addCompanyTab;
    private AddDepotTab addDepotTab;
    private AddNewModelsTab addNewModelsTab;



    public FleetManagementView(FleetManagementPresenter presenter, ApplicationContext applicationContext) {
        tabs = new TabSheet();
        this.applicationContext = applicationContext;
        initializeTabLayout();
    }

    private void initializeTabLayout() {
        addCompanyTab = new AddCompanyTab(applicationContext.getBean(AddCompanyPresenter.class));
        addDepotTab = new AddDepotTab(applicationContext.getBean(AddDepotPresenter.class));
        addNewModelsTab = new AddNewModelsTab(applicationContext.getBean(AddNewModelsPresenter.class));
        tabs.add("Companies", addCompanyTab);
        tabs.add("Depot Management", addDepotTab);
        tabs.add("Bus Models", addNewModelsTab);
        tabs.setSizeFull();
        add(tabs);
    }


}

