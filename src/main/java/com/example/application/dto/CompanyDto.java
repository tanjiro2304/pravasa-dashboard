package com.example.application.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {
    private Long id;
    private String companyName;
    private String address;
    private String helpline;
    private String email;
}