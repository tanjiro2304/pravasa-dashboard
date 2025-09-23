package info.pravasa.application.views.dashboard.metro.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.utils.GridCommonUtils;
import info.pravasa.dto.PlanningAuthorityDto;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PlanningAuthorityView extends VerticalLayout {

    private TextField contactField;
    private TextField entityField;
    private EmailField emailField;
    private TextArea addressField;
    private Binder<PlanningAuthorityDto> binder;
    private Button button;
    private VerticalLayout fieldLayout;
    private Grid<PlanningAuthorityDto> planningAuthorityDtoGrid;
    private final Consumer<PlanningAuthorityDto> planningAuthorityDtoConsumer;
    private final Supplier<List<PlanningAuthorityDto>> listSupplier;


    public PlanningAuthorityView(Consumer<PlanningAuthorityDto> planningAuthorityDtoConsumer, Supplier<List<PlanningAuthorityDto>> listSupplier){
        this.planningAuthorityDtoConsumer = planningAuthorityDtoConsumer;
        this.listSupplier = listSupplier;
        initializeField();
        initializeBinder();
        initializeFieldLayout();
        initializeGrid();
        HorizontalLayout mainLayout = new HorizontalLayout(fieldLayout, planningAuthorityDtoGrid);
        setSizeFull();
        mainLayout.setSizeFull();
        add(mainLayout);
    }

    private void initializeField() {
        entityField = CommonComponent.createTextField("Entity Name", true, true);
        contactField = CommonComponent.createTextField("Contact", true, true);
        emailField = CommonComponent.createEmailField("Email");
        button = new Button("Submit", event -> {
            PlanningAuthorityDto bean = binder.getBean();
            if(Objects.nonNull(bean)){
                planningAuthorityDtoConsumer.accept(bean);
            }
        });
        addressField = new TextArea("Address");
        addressField.setSizeFull();
    }

    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(entityField).bind(PlanningAuthorityDto::getEntityName, PlanningAuthorityDto::setEntityName);
        binder.forField(emailField).bind(PlanningAuthorityDto::getEmail, PlanningAuthorityDto::setEmail);
        binder.forField(contactField).bind(PlanningAuthorityDto::getContactNo, PlanningAuthorityDto::setContactNo);
        binder.forField(addressField).bind(PlanningAuthorityDto::getAddress, PlanningAuthorityDto::setAddress);
        binder.setBean(new PlanningAuthorityDto());
    }

    private void initializeFieldLayout() {
        fieldLayout = new VerticalLayout(entityField, contactField, emailField, addressField, button);
    }

    private void initializeGrid() {
        planningAuthorityDtoGrid = new Grid<>();
        planningAuthorityDtoGrid.setSizeFull();
        GridCommonUtils.createColumn(planningAuthorityDtoGrid, PlanningAuthorityDto::getEntityName, "Entity Name", "ENTITY_NAME");
        GridCommonUtils.createColumn(planningAuthorityDtoGrid, PlanningAuthorityDto::getAddress, "Address", "ADDRESS");
        GridCommonUtils.createColumn(planningAuthorityDtoGrid, PlanningAuthorityDto::getContactNo, "Contact No", "CONTACT_NO");
        GridCommonUtils.createColumn(planningAuthorityDtoGrid, PlanningAuthorityDto::getEmail, "Email", "EMAIL");

        planningAuthorityDtoGrid.setItems(listSupplier.get());
    }
}
