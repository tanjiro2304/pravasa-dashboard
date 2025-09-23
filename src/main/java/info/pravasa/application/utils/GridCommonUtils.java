package info.pravasa.application.utils;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.function.ValueProvider;

public class GridCommonUtils {

    public static <T> Grid.Column<T> createColumn(Grid<T> grid, ValueProvider<T, ?> valueProvider,String header, String key){
        return grid.addColumn(valueProvider)
                .setHeader(header)
                .setKey(key)
                .setSortable(true)
                .setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.CENTER);
    }
}
