package info.pravasa.application.views.dashboard.roadTransport.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.dataview.RadioButtonGroupDataView;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.views.dashboard.roadTransport.components.AddRouteDialog;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.HaltDto;
import info.pravasa.dto.RouteDto;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.checkerframework.checker.guieffect.qual.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@UIScope
@SpringComponent
public class RoutesTab extends VerticalLayout {

    private ComboBox<Company> companyComboBox;

    private ComboBox<DepotDto> depotDtoComboBox;

    private Grid<RouteDto> routeDtoGrid;

    private Grid<HaltDto> haltDtoGrid;

    private List<RouteDto> routeDtos = new ArrayList<>();

    private List<HaltDto> haltDtos = new ArrayList<>();

    private VerticalLayout routeGridLayout;

    private VerticalLayout haltGridLayout;


    @Setter
    private Consumer<RouteDto> routeDtoConsumer;

    @Setter
    private Supplier<List<DepotDto>> depotDtoSupplier;



    @PostConstruct
    private void init(){
        initializeComboBoxes();
        initializeRouteGrid();
        initializeHaltGrid();
        setSpacing(false);
        setPadding(false);
        setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout(routeGridLayout, haltGridLayout);
        horizontalLayout.setSizeFull();
        horizontalLayout.setPadding(false);
        horizontalLayout.setSpacing(false);
        add(depotDtoComboBox, horizontalLayout);
    }

    private void initializeComboBoxes() {
        depotDtoComboBox = new ComboBox<>("Select Depot");
        depotDtoComboBox.setItemLabelGenerator(DepotDto::getDepotName);


    }

    private void initializeRouteGrid() {
        routeDtoGrid = new Grid<>();
        Button addRouteButton = new Button("Add Route", event ->{
            if(Objects.isNull(depotDtoComboBox.getValue())){
                Notification.show("Please select a depot first.", 3000, Notification.Position.MIDDLE);
                return;
            }
            AddRouteDialog dialog = new AddRouteDialog(routeDtoConsumer, depotDtoComboBox.getValue());
            dialog.open();
        });
        routeGridLayout = new VerticalLayout(addRouteButton, routeDtoGrid);
        routeGridLayout.setSpacing(false);
        routeGridLayout.setPadding(false);
        routeGridLayout.setSizeFull();
        routeDtoGrid.setSizeFull();
        routeDtoGrid.addColumn(RouteDto::getRouteNo).setHeader("Route No");
        routeDtoGrid.addColumn(RouteDto::getSource).setHeader("Source");
        routeDtoGrid.addColumn(RouteDto::getDestination).setHeader("Destination");
        routeDtoGrid.addColumn(RouteDto::getDistance).setHeader("Distance");

    }

    private void initializeHaltGrid() {
        haltDtoGrid = new Grid<>();
        Button addHaltButton = new Button("Add Halt");

        haltGridLayout = new VerticalLayout(addHaltButton, haltDtoGrid);
        haltGridLayout.setPadding(false);
        haltGridLayout.setSpacing(false);
        haltGridLayout.setSizeFull();
        haltDtoGrid.setSizeFull();
        haltDtoGrid.addColumn(HaltDto::getHaltPosition).setHeader("Position");
        haltDtoGrid.addColumn(HaltDto::getStopName).setHeader("Stop");
    }

    public void setDepotCombox(){
        depotDtoComboBox.setItems(depotDtoSupplier.get());
    }

}
