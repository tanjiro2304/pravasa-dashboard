package info.pravasa.application.views.dashboard.roadTransport;




import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.Menu;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.utils.CommonUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.views.dashboard.roadTransport.components.AddBusModel;
import info.pravasa.application.views.dashboard.roadTransport.tabs.DepotTab;
import info.pravasa.application.views.dashboard.roadTransport.tabs.ManualData;
import info.pravasa.application.views.dashboard.roadTransport.tabs.RoutesTab;
import info.pravasa.application.views.dashboard.roadTransport.tabs.StopTab;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Setter;

import java.util.Objects;

@UIScope
@SpringComponent
public class RoadTransportView extends VerticalLayout {

    @Resource
    private RoadTransportPresenter roadTransportPresenter;

    @Resource
    private DepotTab depotTab;

    @Resource
    private RoutesTab routesTab;

    @Resource
    private StopTab stopsTab;

    @Resource
    private ManualData manualData;

    private ComboBox<Company> companyComboBox;

    private TabSheet tabSheet;


    @Setter
    private Company selectedCompany;
    private NativeLabel title;

    private Binder<DepotDto> binder;
    private VerticalLayout fieldsLayout;
    private MenuBar menuBar;
    @PostConstruct
    private void init(){
        if(CommonUtils.verifyUserLogin()){
            depotTab.setDepotFunction(id -> roadTransportPresenter.fetchDepotByCompany(id));
            depotTab.setDepotDtoConsumer(dto -> roadTransportPresenter.saveData(dto));
            stopsTab.setStopDtoConsumer(dto -> roadTransportPresenter.saveStop(dto));
            stopsTab.setListConsumer((id) -> roadTransportPresenter.findStop(id));
            initializeMenuBar();
            setSizeFull();
            initializeCompanyComboBox();
            initializeTab();
            add(menuBar,new HorizontalLayout(companyComboBox), tabSheet);
        }
    }

    private void initializeMenuBar() {
        menuBar = new MenuBar();

        menuBar.addItem("Add New Bus Model", e -> {
            AddBusModel addBusModel = new AddBusModel(busModelDto -> roadTransportPresenter.saveBusModel(busModelDto)
            , () -> roadTransportPresenter.fetchAllModels());
            addBusModel.open();
        });
    }


    private void initializeCompanyComboBox() {
        companyComboBox = new ComboBox<>();
        companyComboBox.setItems(roadTransportPresenter.fetchAllCompanies());
        companyComboBox.setItemLabelGenerator(Company::getCompanyName);
        companyComboBox.setWidth("25rem");
        companyComboBox.addValueChangeListener(event ->{
            depotTab.setSelectedCompany(event.getValue());
            routesTab.setDepotDtoSupplier(() -> roadTransportPresenter.fetchDepotByCompany(event.getValue().getId()));
            routesTab.setDepotCombox();
            routesTab.setRouteDtoConsumer(routeDto -> {
                roadTransportPresenter.saveRoute(routeDto);
            });
            stopsTab.setSelectedCompany(event.getValue());
            depotTab.refreshGrid();
            stopsTab.refreshGrid();
        });


    }

    private void initializeTab() {
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Stats",new VerticalLayout());
        tabSheet.add("Depots",depotTab);
        tabSheet.add("Routes",routesTab);
        tabSheet.add("Stops",stopsTab);
        tabSheet.add("Photos",new VerticalLayout());
        tabSheet.add("Manual Data",manualData);
    }

}
