package com.example.application.views.login;


import com.example.application.services.LoginService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.UserInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientException;

@UIScope
@SpringComponent
@Slf4j
public class LoginPresenter {

    @Resource
    private LoginService loginService;

    public UserInfo userLogin(UserInfo loginUserDto){
        try{
            return loginService.login(loginUserDto);
        }catch(WebClientException webClientException){
            log.error("Error while login: {}", webClientException.getMessage());
            return null;
        }
    }
}
