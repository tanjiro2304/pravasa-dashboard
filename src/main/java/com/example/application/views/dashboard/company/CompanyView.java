package com.example.application.views.dashboard.company;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;


import java.util.Objects;


@UIScope
@SpringComponent
public class CompanyView extends VerticalLayout {

    private Grid<Company> companyGrid;

    @Resource
    private CompanyPresenter companyPresenter;

    @PostConstruct
    public void init() {
        if(Objects.isNull(VaadinSession.getCurrent().getAttribute("token"))){
            Notification.show("Please Login");
            UI.getCurrent().navigate("/");
        }
        else{
            setSizeFull();
            initializelayout();
            setData();
            addComponentsToLayout();
        }

    }

    private void initializelayout() {
        companyGrid = new Grid<>();
        companyGrid.setSizeFull();
        initializeColumns();
    }

    private void initializeColumns() {
        companyGrid.addColumn(Company::getCompanyName).setHeader("Company Name");
        companyGrid.addColumn(Company::getCityName).setHeader("City");
        companyGrid.addColumn(Company::getCompanyType).setHeader("Company Type");
        companyGrid.addColumn(Company::getAddress).setHeader("Address");
        companyGrid.addColumn(Company::getEmail).setHeader("Email");
    }

    private void setData(){
        companyGrid.setItems(companyPresenter.fetchAllCompanies());
    }

    public void addComponentsToLayout(){
        add(companyGrid);
    }


}
