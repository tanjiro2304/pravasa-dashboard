package info.pravasa.application.views.dashboard.metro;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.utils.CommonComponent;
import info.pravasa.application.views.dashboard.metro.tabs.MetroLineView;
import info.pravasa.application.views.dashboard.metro.tabs.OperatorView;
import info.pravasa.application.views.dashboard.metro.tabs.PlanningAuthorityView;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;


@UIScope
@SpringComponent
public class MetroDashboardView extends VerticalLayout {

    @Resource
    private MetroDashBoardPresenter metroDashBoardPresenter;
    private OperatorView operatorView;
    private MetroLineView metroLineView;
    private PlanningAuthorityView planningAuthorityView;
    private TabSheet tabSheet;
    private Tabs tabs;

    @PostConstruct
    private void init(){
        initializeTabs();
        initializeTabSheet();
        setSizeFull();
        add(tabSheet);
    }

    private void initializeTabs() {
        metroLineView = new MetroLineView();
        operatorView = new OperatorView(dto -> metroDashBoardPresenter.save(dto),() -> metroDashBoardPresenter.findAll());
        planningAuthorityView = new PlanningAuthorityView( dto -> metroDashBoardPresenter.save(dto), () -> metroDashBoardPresenter.findAllPlanningAuthority());
    }

    private void initializeTabSheet() {
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Planning Authority", CommonComponent.createTabLayout(planningAuthorityView));
        tabSheet.add("Operator", CommonComponent.createTabLayout(operatorView));
        tabSheet.add("Metro Line View", CommonComponent.createTabLayout(metroLineView));
    }

}
