package info.pravasa.application.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Collection;
import java.util.Map;

public class CommonComponent {

    public static TextField createTextField(String caption, boolean isMandatory, boolean isFullSize){
        TextField textField = new TextField(caption);
        textField.setRequired(isMandatory);
        textField.setWidthFull();
        return textField;
    }

    public static <T> MultiSelectComboBox<T> createMultiSelectComboBox(String caption, boolean isMandatory, boolean isFullSize, Collection<T> items){
        MultiSelectComboBox<T> comboBox = new MultiSelectComboBox<>(caption);
        comboBox.setRequired(isMandatory);
        comboBox.setItems(items);
        comboBox.setWidthFull();
        return comboBox;
    }

    public static <T> ComboBox<T> createComboBox(String caption, boolean isMandatory, boolean isFullSize, Collection<T> items){
        ComboBox<T> comboBox = new ComboBox<>(caption);
        comboBox.setRequired(isMandatory);
        comboBox.setItems(items);
        comboBox.setWidthFull();
        return comboBox;
    }

    public static EmailField createEmailField(String caption){
        EmailField emailField = new EmailField(caption);
        emailField.setRequired(true);
        emailField.setWidthFull();
        return emailField;
    }

    public static NumberField createNumberField(String caption, boolean isMandatory, boolean isFullSize){
        NumberField numberField = new NumberField(caption);
        numberField.setRequired(isMandatory);
        numberField.setWidthFull();
        return numberField;
    }

    public static Div createTabLayout(Component component){
        Div div = new Div(component);
        div.setSizeFull();
        div.getElement().getStyle().set("margin","1rem");
        return div;
    }

    public static TabSheet createTabSheet(Map<String, Component> componentMap){
        TabSheet tabSheet = new TabSheet();
        componentMap.forEach(tabSheet::add);
        return tabSheet;
    }

    public static HorizontalLayout createRow(Component left, Component right) {
        HorizontalLayout row = new HorizontalLayout(left, right);
        row.setWidthFull();
        row.setSpacing(true);

        // Make both fields take equal space
        row.setFlexGrow(1, left, right);

        return row;
    }


}
