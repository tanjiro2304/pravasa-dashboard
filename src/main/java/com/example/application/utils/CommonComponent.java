package com.example.application.utils;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class CommonComponent {

    public static TextField createTextField(String caption){
        TextField textField = new TextField(caption);
        textField.setRequired(true);
        textField.setSizeFull();
        return textField;
    }

    public static EmailField createEmailField(String caption){
        EmailField emailField = new EmailField(caption);
        emailField.setRequired(true);
        emailField.setSizeFull();
        return emailField;
    }
}
