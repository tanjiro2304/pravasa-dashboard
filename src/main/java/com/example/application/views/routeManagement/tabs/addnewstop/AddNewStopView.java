package com.example.application.views.routeManagement.tabs.addnewstop;

import com.example.application.dto.CompanyDto;
import com.example.application.dto.StopDto;
import com.example.application.views.routeManagement.RouteManagementPresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class AddNewStopView extends VerticalLayout {
    private TextField stopName;
    private Long companyId;
    private NumberField longitude;
    private NumberField latitude;
    private ComboBox<CompanyDto> comboBox;
    private RouteManagementPresenter routeManagementPresenter;
    private Button save;
    private Button cancel;
    private Binder<StopDto> binder;
    public AddNewStopView(RouteManagementPresenter routeManagementPresenter) {
        this.routeManagementPresenter = routeManagementPresenter;
        initializeFields();
        initializeLayout();

    }

    private void initializeFields() {
        stopName = new TextField("Stop Name");
        latitude = new NumberField("Latitude");
        longitude = new NumberField("Longitude");
        comboBox = new ComboBox<>("Transport Company");
        comboBox.setItems(routeManagementPresenter.getCompanies());
        comboBox.setItemLabelGenerator(CompanyDto::getCompanyName);
        save = new Button("Save", buttonClickEvent -> routeManagementPresenter.saveStop(binder.getBean()));
        cancel = new Button("Cancel");
        binder = new Binder<>();
        binder.forField(stopName).bind(StopDto::getStopName,StopDto::setStopName);
        binder.forField(latitude).bind(StopDto::getLatitude,StopDto::setLatitude);
        binder.forField(longitude).bind(StopDto::getLongitude,StopDto::setLongitude);
        binder.forField(comboBox).bind(stopDto -> {
            return routeManagementPresenter.findById(stopDto.getCompanyId());
        },(dto, value) -> dto.setCompanyId(value.getId()));
        binder.setBean(new StopDto());
    }

    private void initializeLayout(){

        VerticalLayout mainLayout = new VerticalLayout(createRow(stopName,latitude),createRow(longitude,comboBox),createRow(save,cancel));
        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");
        mainLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        add(mainLayout);
        add();
    }

    private HorizontalLayout createRow(Component... components){
        HorizontalLayout layout = new HorizontalLayout(components);
        layout.setWidth("100%");
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return layout;
    }


}
