package com.example.application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;
}
