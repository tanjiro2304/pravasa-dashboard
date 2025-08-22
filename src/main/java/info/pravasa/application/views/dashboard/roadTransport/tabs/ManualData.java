package info.pravasa.application.views.dashboard.roadTransport.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.services.BusModelService;
import info.pravasa.application.services.DepotService;
import info.pravasa.application.services.RouteService;
import info.pravasa.application.services.StopService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.util.List;

@UIScope
@SpringComponent
public class ManualData extends VerticalLayout {
    private TextArea textArea;

    private ComboBox<String> dataTypeComboBox;
    private Button submitButton;
    private Button clearButton;

    @Resource
    private StopService stopService;
    @Resource
    private BusModelService busModelService;
    @Resource
    private RouteService routeService;

    @Resource
    private DepotService depotService;
    private List<String> dataTypes = List.of( "Route", "Halt", "Depot", "Company");

    @PostConstruct
    private void init(){
        dataTypeComboBox = new ComboBox<>("Select Data Type");
        dataTypeComboBox.setItems(dataTypes);
        textArea = new TextArea("JSON Data");
        textArea.setMaxLength(4000);
        textArea.setHeight("15rem");
        textArea.setWidthFull();
        submitButton = new Button("Submit");
        clearButton = new Button("Clear", event -> {
            textArea.clear();
        });
        setSizeFull();
        add(dataTypeComboBox, textArea, new HorizontalLayout(submitButton, clearButton));
        submitButton.addClickListener(event -> {
            if(dataTypeComboBox.getValue().equalsIgnoreCase("route")){
                routeService.saveJson(textArea.getValue());
            }
            if(dataTypeComboBox.getValue().equalsIgnoreCase("stop")){
                stopService.saveJson(textArea.getValue());
            }

        });
    }
}
