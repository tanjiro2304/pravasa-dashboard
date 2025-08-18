package com.example.application.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Map;

public class CommonComponent {

    public static TextField createTextField(String caption, boolean isMandatory, boolean isFullSize){
        TextField textField = new TextField(caption);
        textField.setRequired(isMandatory);
        if(isFullSize){
            textField.setSizeFull();
        }
        return textField;
    }

    public static EmailField createEmailField(String caption){
        EmailField emailField = new EmailField(caption);
        emailField.setRequired(true);
        emailField.setSizeFull();
        return emailField;
    }

    public static NumberField createNumberField(String caption, boolean isMandatory, boolean isFullSize){
        NumberField numberField = new NumberField(caption);
        numberField.setRequired(isMandatory);
        if(isFullSize){
            numberField.setSizeFull();
        }
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


}
