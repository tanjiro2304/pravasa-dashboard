package com.example.application.views.dashboard.roadTransport;




import com.example.application.utils.CommonComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
public class RoadTransportView extends VerticalLayout {

    @Resource
    private RoadTransportPresenter roadTransportPresenter;

    private ComboBox<Company> companyComboBox;

    private TabSheet tabSheet;


    @Setter
    private Company selectedCompany;
    private DepotDto selectedDepot;
    private NativeLabel title;
    private HorizontalLayout depotTab;
    private Grid<DepotDto> depotDtoGrid;
    private Binder<DepotDto> binder;

    @PostConstruct
    private void init(){
        initializeCompanyComboBox();
        initializeTitle();
        initializeDepotTab();
        initializeDepotGrid();
        initializeTab();
        initializeSlideTab();
        add(companyComboBox, tabSheet);
    }

    private void initializeDepotTab(){
        this.depotTab = new HorizontalLayout();
        TextField depotName = CommonComponent.createTextField("Depot Name", true,true);
        TextField depotCode = CommonComponent.createTextField("Depot Code", true,true);
        NumberField longitude = CommonComponent.createNumberField("Latitude", true,true);
        NumberField latitude = CommonComponent.createNumberField("Longitude", true,true);
        EmailField email = CommonComponent.createEmailField("Email");
        TextField contact = CommonComponent.createTextField("Contact", true,true);
        TextField address = CommonComponent.createTextField("Address", true,true);
        Button submitButton = new Button("Submit");
        VerticalLayout fieldsLayout = new VerticalLayout(new HorizontalLayout(depotName,depotCode),
                new HorizontalLayout(latitude,longitude),
                new HorizontalLayout(email,contact),address,submitButton);
        fieldsLayout.setWidth("50%");
        binder = new Binder<>();
        binder.forField(depotName)
                .asRequired("Depot Name is required")
                .bind(DepotDto::getDepotName, DepotDto::setDepotName);

        binder.forField(depotCode)
                .asRequired("Depot Code is required")
                .bind(DepotDto::getDepotCode, DepotDto::setDepotCode);

        binder.forField(longitude)
                .asRequired("Longitude is required")
                .bind(DepotDto::getLongitude, DepotDto::setLongitude);

        binder.forField(latitude)
                .asRequired("Latitude is required")
                .bind(DepotDto::getLatitude, DepotDto::setLatitude);

        binder.forField(email)
                .asRequired("Email is required")
                .withValidator(new EmailValidator("Enter a valid email address"))
                .bind(DepotDto::getEmail, DepotDto::setEmail);

        binder.forField(contact)
                .asRequired("Contact number is required")
                .withValidator(c -> c.matches("\\d{10}"), "Enter a valid 10-digit contact number")
                .bind(DepotDto::getContact, DepotDto::setContact);

        binder.forField(address)
                .asRequired("Address is required")
                .bind(DepotDto::getAddress, DepotDto::setAddress);

        submitButton.addClickListener(event -> {
            if(Objects.isNull(selectedDepot)) {
                selectedDepot = new DepotDto();
            }
            try {
                if(binder.isValid()) {
                    binder.writeBean(selectedDepot);
                    if(selectedDepot.getCompanyId() == null){
                        selectedDepot.setCompanyId(selectedCompany.getId());
                    }
                    roadTransportPresenter.saveData(selectedDepot);
                    refreshGrid();
                    binder.readBean(new DepotDto());
                }else{
                    binder.validate();
                }

            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        });
        depotTab.add(fieldsLayout);
    }

    private void refreshGrid(){
        depotDtoGrid.setItems(roadTransportPresenter.fetchDepotByCompany(selectedCompany.getId()));

    }


    private void initializeDepotGrid() {
        depotDtoGrid = new Grid<>();
        depotDtoGrid.addColumn(DepotDto::getDepotName).setHeader("Depot Name");
        depotDtoGrid.addColumn(DepotDto::getDepotCode).setHeader("Depot Code");
        depotDtoGrid.addColumn(DepotDto::getAddress).setHeader("Address");
        depotDtoGrid.addColumn(DepotDto::getContact).setHeader("Contact");
        depotDtoGrid.addColumn(DepotDto::getEmail).setHeader("Email");
        depotDtoGrid.addItemClickListener(event -> {
            selectedDepot = event.getItem();
            binder.readBean(selectedDepot);
        });
        depotTab.add(depotDtoGrid);
    }

    private void initializeTitle() {
    }

    private void initializeCompanyComboBox() {
        companyComboBox = new ComboBox<>();
        companyComboBox.setItems(roadTransportPresenter.fetchAllCompanies());
        companyComboBox.setItemLabelGenerator(Company::getCompanyName);
        companyComboBox.setWidth("25rem");
        companyComboBox.addValueChangeListener(event ->{
            setSelectedCompany(event.getValue());
            depotDtoGrid.setItems(roadTransportPresenter.fetchDepotByCompany(selectedCompany.getId()));
        });
    }

    private void initializeTab() {
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Stats",new VerticalLayout());
        tabSheet.add("Depots",depotTab);
        tabSheet.add("Routes",new VerticalLayout());
        tabSheet.add("Photos",new VerticalLayout());
    }

    private void initializeSlideTab(){

    }
}
