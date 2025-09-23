package info.pravasa.application.views.dashboard.metro.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.utils.GridCommonUtils;
import info.pravasa.dto.OperatorDto;
import jakarta.annotation.PostConstruct;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class OperatorView extends VerticalLayout {

    private TextField operatorName;
    private TextField contactNo;
    private EmailField emailField;
    private Binder<OperatorDto> operatorDtoBinder;
    private VerticalLayout fieldLayout;
    private Grid<OperatorDto> operatorDtoGrid;
    private Button button;

    private final Consumer<OperatorDto> operatorDtoConsumer;
    private final Supplier<List<OperatorDto>> operatorDtoSupplier;


    public OperatorView(Consumer<OperatorDto> operatorDtoConsumer, Supplier<List<OperatorDto>> operatorDtoSupplier){
        this.operatorDtoConsumer = operatorDtoConsumer;
        this.operatorDtoSupplier = operatorDtoSupplier;
        initializeFields();
        initializeBinder();
        initializeFieldLayout();
        initializeGrid();
        setSizeFull();
        HorizontalLayout mainLayout = new HorizontalLayout(fieldLayout, operatorDtoGrid);
        mainLayout.setSizeFull();
        add(mainLayout);
    }

    private void initializeFields() {
        operatorName = CommonComponent.createTextField("Operator Name", true,true);
        contactNo = CommonComponent.createTextField("Contact No", true, true);
        emailField = CommonComponent.createEmailField("Email");
        button = new Button("Submit", event -> {
            OperatorDto bean = operatorDtoBinder.getBean();
            if(!Objects.isNull(bean)){
                operatorDtoConsumer.accept(bean);
                operatorDtoBinder.setBean(new OperatorDto());
                refreshGrid();
            }
        });
    }

    private void initializeBinder(){
        operatorDtoBinder = new Binder<OperatorDto>();
        operatorDtoBinder.forField(operatorName).bind(OperatorDto::getOperatorName, OperatorDto::setOperatorName);
        operatorDtoBinder.forField(contactNo).bind(OperatorDto::getContactNo, OperatorDto::setContactNo);
        operatorDtoBinder.forField(emailField).bind(OperatorDto::getEmail, OperatorDto::setEmail);
        operatorDtoBinder.setBean(new OperatorDto());
    }
    private void initializeFieldLayout() {
        fieldLayout = new VerticalLayout(operatorName, contactNo, emailField, button);
    }

    private void initializeGrid() {
        operatorDtoGrid = new Grid<>();
        operatorDtoGrid.setSizeFull();
        GridCommonUtils.createColumn(operatorDtoGrid, OperatorDto::getOperatorName, "Operator Name", "OPERATOR_NAME");
        GridCommonUtils.createColumn(operatorDtoGrid, OperatorDto::getEmail, "Email", "EMAIL");
        GridCommonUtils.createColumn(operatorDtoGrid, OperatorDto::getContactNo, "Contact", "CONTACT");
        operatorDtoGrid.setItems(operatorDtoSupplier.get());
    }

    private void refreshGrid(){
        operatorDtoGrid.setItems(operatorDtoSupplier.get());
    }
}
