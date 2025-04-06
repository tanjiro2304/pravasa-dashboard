package com.example.application.views.fleetManagement.tabs.addCompany;

import com.example.application.dto.CompanyDto;
import com.example.application.utils.CommonComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AddCompanyTab extends VerticalLayout {

    private final Binder<CompanyDto> binder = new Binder<>();
    private final AddCompanyPresenter presenter;
    private Grid<CompanyDto> grid;
    public AddCompanyTab(AddCompanyPresenter presenter){
        this.presenter = presenter;
        add(initializeFields(), initializeButtonLayout(), initializeGrid());
    }

    public FormLayout initializeFields(){
        TextField companyNameField = CommonComponent.createTextField("Company Name");
        TextField addressField = CommonComponent.createTextField("Address");
        TextField helplineField = CommonComponent.createTextField("Helpline");
        EmailField emailField = CommonComponent.createEmailField("Email");
        binder.forField(companyNameField)
                .asRequired()
                .bind(CompanyDto::getCompanyName, CompanyDto::setCompanyName);
        binder.forField(addressField)
                .asRequired()
                .bind(CompanyDto::getAddress, CompanyDto::setAddress);
        binder.forField(helplineField)
                .asRequired()
                .bind(CompanyDto::getHelpline, CompanyDto::setHelpline);
        binder.forField(emailField)
                .asRequired()
                .bind(CompanyDto::getEmail, CompanyDto::setEmail);
        binder.setBean(new CompanyDto());
        return new FormLayout(companyNameField, addressField, helplineField, emailField);
    }

    public HorizontalLayout initializeButtonLayout(){
        Button saveButton = new Button("Save", event -> {
            if(binder.isValid()) {
                presenter.saveCompany(binder.getBean());
                binder.setBean(new CompanyDto());
                grid.setItems(presenter.findAll());
            }else {
                Notification.show("Please fill all data", 2500, Notification.Position.MIDDLE);
            }
        });
        Button cancelButton = new Button("Cancel", event -> {
            binder.readBean(new CompanyDto());
        });
        return new HorizontalLayout(saveButton, cancelButton);
    }

    private Grid<CompanyDto> initializeGrid(){
        grid = new Grid<>();
        grid.addColumn(CompanyDto::getCompanyName).setHeader("Company");
        grid.addColumn(CompanyDto::getHelpline).setHeader("Company");
        grid.addColumn(CompanyDto::getAddress).setHeader("Company");
        grid.addColumn(CompanyDto::getEmail).setHeader("Company");

        grid.setItems(presenter.findAll());

        return grid;
    }
}
