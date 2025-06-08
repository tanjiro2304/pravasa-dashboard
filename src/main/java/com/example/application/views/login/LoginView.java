package com.example.application.views.login;

import com.example.application.dto.LoginUserDto;
import com.example.application.services.LoginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.Resource;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Objects;

@PageTitle("Login Page")
@Route("")
@Menu(order = 1, icon = LineAwesomeIconUrl.SIGN_IN_ALT_SOLID)
@SpringComponent
@UIScope
public class LoginView extends VerticalLayout {

    private TextField emailField;

    private PasswordField passwordField;

    private Button submit;

    private FormLayout formLayout;

    private Binder<LoginUserDto> binder;

    private LoginPresenter loginPresenter;

    public LoginView(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        setSizeFull();
        initializeField();
        initializeLayout();
        initializeBinder();
        add(formLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
    }

    private void initializeLayout() {
        formLayout = new FormLayout(emailField,passwordField,submit);
        formLayout.setColspan(emailField, 1);
        formLayout.setColspan(passwordField, 1);
        formLayout.setColspan(submit, 1);
        formLayout.setHeightFull();
        formLayout.setMaxWidth("35rem");

    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(emailField).bind(LoginUserDto::getEmail,LoginUserDto::setEmail);
        binder.forField(passwordField).bind(LoginUserDto::getPassword, LoginUserDto::setPassword);
        binder.setBean(new LoginUserDto());
    }

    private void initializeField() {
        emailField = new TextField("Username");
        emailField.setWidth("7rem");
        passwordField = new PasswordField("Password");
        passwordField.setWidth("7rem");
        submit = new Button("Submit");
        submit.addClickListener(event -> {
            if(binder.isValid()){
                onSave();
            }else{
                Notification.show("Please Add proper data");
            }
        });
        submit.setWidth("7rem");
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void onSave(){
        LoginUserDto login = loginPresenter.userLogin(binder.getBean());
        if(Objects.nonNull(login)){
            VaadinSession.getCurrent().setAttribute("token", login.getToken());
            System.out.println(login.getToken());
        }else{
            Notification.show("Invalid Username or Password");
        }

    }
}
