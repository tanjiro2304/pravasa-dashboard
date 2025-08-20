package info.pravasa.application.views.dashboard;

import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.views.dashboard.company.CompanyView;
import info.pravasa.application.views.dashboard.roadTransport.RoadTransportView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.util.List;

@PageTitle("DashBoard")
@Route("/dashBoard")
public class MainView extends VerticalLayout {

    @Resource
    private MainPresenter mainPresenter;

    @Resource
    private CompanyView companyView;

    @Resource
    private RoadTransportView roadTransportView;

    private TabSheet tabSheet;

    private Tabs tabs;

    private List<String> tabViews = List.of("Company");

    @PostConstruct
    public void init(){
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        initializeTabs();
        add(tabSheet);
    }

    private void initializeTabs() {

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Company", CommonComponent.createTabLayout(companyView));
        tabSheet.add("Road Transport", CommonComponent.createTabLayout(roadTransportView));
        tabSheet.add("Metro", new VerticalLayout());
        tabSheet.add("Railways", new VerticalLayout());
        tabSheet.add("Water Transport", new VerticalLayout());
    }
}
