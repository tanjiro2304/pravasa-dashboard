package com.example.application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusDto {
    private Long busTypeId;
    private String modelName;
    private String manufacturer;
    private Boolean isAirConditioned;
    private String floorType;
    private Object busType;
}
