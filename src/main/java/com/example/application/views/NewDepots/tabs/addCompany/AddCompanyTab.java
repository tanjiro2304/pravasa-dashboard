package com.example.application.views.NewDepots.tabs.addCompany;

import com.example.application.dto.CompanyDto;
import com.example.application.views.NewDepots.FleetManagementPresenter;
import com.example.application.views.NewDepots.tabs.addDepot.AddDepotTab;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AddCompanyTab extends VerticalLayout {
    private final Binder<CompanyDto> binder = new Binder<>(CompanyDto.class);
    private AddCompanyPresenter presenter;
    public AddCompanyTab(AddCompanyPresenter presenter){
        this.presenter = presenter;
        add(initializeFields(), initializeButtonLayout());
    }

    public FormLayout initializeFields(){
        TextField companyNameField = new TextField("Company Name");
        TextField addressField = new TextField("Address");
        TextField helplineField = new TextField("Helpline");
        EmailField emailField = new EmailField("Email");
        binder.forField(companyNameField).bind(CompanyDto::getCompanyName, CompanyDto::setCompanyName);
        binder.forField(addressField).bind(CompanyDto::getAddress, CompanyDto::setAddress);
        binder.forField(helplineField).bind(CompanyDto::getHelpline, CompanyDto::setHelpline);
        binder.forField(emailField).bind(CompanyDto::getEmail, CompanyDto::setEmail);
        binder.setBean(new CompanyDto());
        return new FormLayout(companyNameField, addressField, helplineField, emailField);
    }

    public HorizontalLayout initializeButtonLayout(){
        Button saveButton = new Button("Save"/*, event -> presenter.onSaveCompany(binder.getBean())*/);
        Button cancelButton = new Button("Cancel", event -> {
            binder.readBean(new CompanyDto());
        });
        return new HorizontalLayout(saveButton, cancelButton);
    }
}
