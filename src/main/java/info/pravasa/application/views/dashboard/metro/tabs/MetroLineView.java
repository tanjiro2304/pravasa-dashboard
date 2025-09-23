package info.pravasa.application.views.dashboard.metro.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.MetroLineDto;
import org.checkerframework.checker.guieffect.qual.UI;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MetroLineView extends VerticalLayout {
    private Grid<MetroLineDto> metroLineDtoGrid;
    private final Supplier<List<MetroLineDto>> metroLineDtoSupplier;
    private final Consumer<MetroLineDto>  metroLineDtoConsumer;
    private VerticalLayout buttonLayout;

    public MetroLineView(Supplier<List<MetroLineDto>> metroLineDtoSupplier, Consumer<MetroLineDto>  metroLineDtoConsumer){
        this.metroLineDtoConsumer = metroLineDtoConsumer;
        this.metroLineDtoSupplier = metroLineDtoSupplier;
        initializeGrid();
        initializeButtonLayout();
    }

    private void initializeButtonLayout() {

    }

    private void initializeGrid() {
        metroLineDtoGrid = new Grid<>();

    }
}
