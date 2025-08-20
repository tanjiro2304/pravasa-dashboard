package info.pravasa.application.views.dashboard.roadTransport.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.views.dashboard.roadTransport.components.FleetInformationDialog;
import info.pravasa.dto.Company;
import info.pravasa.dto.ContractorDto;
import info.pravasa.dto.DepotDto;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class DepotTab extends VerticalLayout {
    @Setter
    private Company selectedCompany;
    @Setter
    private Function<Long, List<DepotDto>> depotFunction;
    @Setter
    private Consumer<DepotDto> depotDtoConsumer;

    @Setter
    private Supplier<List<Company>> companySupplier;

    private DepotDto selectedDepot;
    private NativeLabel title;
    private HorizontalLayout mainLayout;
    private Grid<DepotDto> depotDtoGrid;
    private Binder<DepotDto> binder;
    private VerticalLayout fieldsLayout;


    @PostConstruct
    private void init(){
        setSizeFull();
        initializeComponents();
        initializeGrid();
        mainLayout = new HorizontalLayout(fieldsLayout,depotDtoGrid);
        mainLayout.setSizeFull();
        add(mainLayout);
    }

    private void initializeComponents() {
        TextField depotName = CommonComponent.createTextField("Depot Name", true,true);
        TextField depotCode = CommonComponent.createTextField("Depot Code", true,true);
        NumberField longitude = CommonComponent.createNumberField("Latitude", true,true);
        NumberField latitude = CommonComponent.createNumberField("Longitude", true,true);
        EmailField email = CommonComponent.createEmailField("Email");
        TextField contact = CommonComponent.createTextField("Contact", true,true);
        TextField address = CommonComponent.createTextField("Address", true,true);
        Button submitButton = new Button("Submit");
        fieldsLayout = new VerticalLayout(new HorizontalLayout(depotName,depotCode),
                new HorizontalLayout(latitude,longitude),
                new HorizontalLayout(email,contact),address,submitButton);
        fieldsLayout.setSizeFull();
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
                    depotDtoConsumer.accept(selectedDepot);
                    refreshGrid();
                    binder.readBean(new DepotDto());
                }else{
                    binder.validate();
                }

            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void initializeGrid(){
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

        depotDtoGrid.addColumn(LitRenderer.<DepotDto>of(
                        "<vaadin-button theme='primary small' @click=${handleEdit}>Edit</vaadin-button>")
                .withFunction("handleEdit", item -> {
                    FleetInformationDialog dialog = new FleetInformationDialog(item);
                    dialog.open();
                })
        ).setHeader("Actions");

        depotDtoGrid.setSizeFull();
    }

    public void refreshGrid(){
        if(Objects.isNull(selectedCompany)){
            depotDtoGrid.setItems(Collections.emptyList());
            return;
        }
        depotDtoGrid.setItems(depotFunction.apply(selectedCompany.getId()));
    }
}
