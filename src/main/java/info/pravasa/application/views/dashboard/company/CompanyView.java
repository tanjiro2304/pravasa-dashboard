package info.pravasa.application.views.dashboard.company;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.utils.CommonUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.City;
import info.pravasa.dto.Company;
import info.pravasa.dto.enums.CompanyType;
import info.pravasa.dto.enums.ModeOfTransport;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Objects;


@UIScope
@SpringComponent
@Slf4j
public class CompanyView extends VerticalLayout {

    private Grid<Company> companyGrid;

    private VerticalLayout fieldsLayout;

    private Binder<Company> binder;
    private Company selectedCompany;

    @Resource
    private CompanyPresenter companyPresenter;

    @PostConstruct
    public void init() {
        if(CommonUtils.verifyUserLogin()){
            initializelayout();
            initializeFieldsLayout();
            setData();
            addComponentsToLayout();
            setSizeFull();
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
        companyGrid.addColumn(Company::getAddress).setHeader("Address");
        companyGrid.addColumn(Company::getEmail).setHeader("Email");
        companyGrid.setSizeFull();
        companyGrid.addItemClickListener(item -> {
            selectedCompany = item.getItem();
            binder.readBean(selectedCompany);
        });
    }

    private void setData(){
        companyGrid.setItems(companyPresenter.fetchAllCompanies());
    }

    public void addComponentsToLayout(){
        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.add(companyGrid);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(false);
        gridLayout.setPadding(false);
        HorizontalLayout horizontalLayout = new HorizontalLayout(fieldsLayout, gridLayout);
        horizontalLayout.setSizeFull();
        add(horizontalLayout);
    }

    public void initializeFieldsLayout(){
        TextField companyName = CommonComponent.createTextField("Company Name", true, true);
        MultiSelectComboBox<City> city = CommonComponent.createMultiSelectComboBox("City", true, true, companyPresenter.fetchAllCities());
        city.setItemLabelGenerator(City::getCityName);
        ComboBox<ModeOfTransport> modeOfTransport = CommonComponent.createComboBox("Mode of Transport", true, true, List.of(ModeOfTransport.values()));
        ComboBox<CompanyType> companyType = CommonComponent.createComboBox("Company Type", true, true, List.of(CompanyType.GOVERNMENT, CompanyType.PRIVATE));
        TextField contactNo = CommonComponent.createTextField("Contact No", true, true);

        EmailField email = CommonComponent.createEmailField("Email");

        TextArea address = new TextArea("Address");
        address.setRequired(true);
        address.setMaxLength(500);
        address.setWidthFull();
        Button submitButton = new Button("Submit");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.setWidthFull();
        Button clearButton = new Button("Clear");
        clearButton.setWidthFull();
        clearButton.addClickListener(event -> {
            selectedCompany = new Company();
            binder.readBean(selectedCompany);
        });
        submitButton.setWidthFull();
        clearButton.setWidthFull();
        binder = new Binder<>(Company.class);

// Company Name
        binder.forField(companyName)
                .asRequired("Company Name is required")
                .bind(Company::getCompanyName, Company::setCompanyName);

// City (MultiSelectComboBox)
        binder.forField(city)
                .asRequired("City is required")
                .bind(Company::getCities, Company::setCities);

// Mode of Transport
        binder.forField(modeOfTransport)
                .asRequired("Mode of Transport is required")
                .bind(Company::getModeOfTransport, Company::setModeOfTransport);

// Company Type
        binder.forField(companyType)
                .asRequired("Company Type is required")
                .bind(Company::getCompanyType, Company::setCompanyType);

// Contact No
        binder.forField(contactNo)
                .asRequired("Contact No is required")
                .withValidator(value -> value.matches("\\d{10}"),
                        "Contact number must be 10 digits")
                .bind(Company::getContactNo, Company::setContactNo);

// Email
        binder.forField(email)
                .asRequired("Email is required")
                .withValidator(new EmailValidator("Enter a valid email"))
                .bind(Company::getEmail, Company::setEmail);

// Address
        binder.forField(address)
                .asRequired("Address is required")
                .bind(Company::getAddress, Company::setAddress);

        submitButton.addClickListener(event -> {
            if(Objects.isNull(selectedCompany)){
                selectedCompany = new Company();
            }

            try {
                binder.writeBean(selectedCompany);
            } catch (ValidationException e) {
                log.error("Validation error: {}", e.getMessage());
            }
            if(binder.isValid()){
                companyPresenter.saveCompany(selectedCompany);
                Notification.show("Data Added Successfully", 3000, Notification.Position.MIDDLE);
                selectedCompany = new Company();
                binder.readBean(selectedCompany);
            }else{
                binder.validate();
            }

        });
        fieldsLayout = new VerticalLayout(
                new HorizontalLayout(companyName, city),
                new HorizontalLayout(modeOfTransport, companyType),
                new HorizontalLayout(contactNo, email),
                address,
                new HorizontalLayout(submitButton, clearButton)
        );
        fieldsLayout.setWidth("50%");
        fieldsLayout.setHeightFull();
    }


}
