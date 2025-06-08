package com.example.application.dto;

import lombok.Data;

@Data
public class CngTypeDto {
    private Long id;
    private Double cngCapacity;
    private Integer noOfTanks;
    private Double mileage;
}
