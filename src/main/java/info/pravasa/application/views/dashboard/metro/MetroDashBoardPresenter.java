package info.pravasa.application.views.dashboard.metro;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.application.services.metro.MetroLineService;
import info.pravasa.application.services.metro.OperatorService;
import info.pravasa.application.services.metro.PlanningAuthorityService;
import info.pravasa.dto.MetroLineDto;
import info.pravasa.dto.OperatorDto;
import info.pravasa.dto.PlanningAuthorityDto;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
public class MetroDashBoardPresenter {

    @Resource
    private OperatorService operatorService;

    @Resource
    private PlanningAuthorityService planningAuthorityService;

    @Resource
    private MetroLineService metroLineService;

    public void save(OperatorDto dto){
        operatorService.save(dto);
    }

    public List<OperatorDto> findAll(){
        return operatorService.findAll();
    }

    public void save(PlanningAuthorityDto dto){
        planningAuthorityService.save(dto);
    }

    public List<PlanningAuthorityDto> findAllPlanningAuthority(){
        return planningAuthorityService.findAll();
    }

    public List<MetroLineDto> findAllMetroLines() {
        return new ArrayList<>();
    }

    public void saveMetroLine(Object dto) {
    }
}
