package com.example.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepotDto {
    private Long id;
    private String depotName;
    private String depotCode;
    private String depotLocation;
    private Long companyId;
    private Double fleetCapacity;
    private Double fleetSize = 0D;
    private Boolean cngRefuelling;
    private Boolean dieselRefuelling;
    private Boolean chargingStation;
    private Double cngBuses = 0D;
    private Double dieselBuses = 0D;
    private Double electricBuses = 0D;
    private Set<Long> busTypes;
}