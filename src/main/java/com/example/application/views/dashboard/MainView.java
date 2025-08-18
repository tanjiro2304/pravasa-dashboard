package com.example.application.views.dashboard;

import com.example.application.utils.CommonComponent;
import com.example.application.views.dashboard.company.CompanyPresenter;
import com.example.application.views.dashboard.company.CompanyView;
import com.example.application.views.dashboard.roadTransport.RoadTransportView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

@PageTitle("DashBoard")
@Route("/dashBoard")
public class MainView extends VerticalLayout {

    @Resource
    private MainPresenter mainPresenter;

    @Resource
    private CompanyView companyView;

    @Resource
    private RoadTransportView roadTransportView;

    private TabSheet tabSheet;

    private List<String> tabViews = List.of("Company");

    @PostConstruct
    public void init(){
        setSizeFull();
        initializeTabs();
        add(tabSheet);
    }

    private void initializeTabs() {
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Company", CommonComponent.createTabLayout(companyView));
        tabSheet.add("Road Transport", CommonComponent.createTabLayout(roadTransportView));
        tabSheet.add("Metro", new VerticalLayout());
        tabSheet.add("Railways", new VerticalLayout());
        tabSheet.add("Water Transport", new VerticalLayout());
    }
}
