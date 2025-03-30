package com.example.application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusType {
    private Long busTypeId;
    private String modelName;
    private Boolean isElectric;
    private Boolean isDiesel;
    private Boolean isCNG;
    private String manufacturer;
    private Double rangeInKm;
    private Boolean isAirConditioned;
    private String floorHeight;
    private Double height;
    private Double length;
    private String emissionNorms;
    private String fleetOwnership;
}
