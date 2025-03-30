package com.example.application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StopDto {

    private Long stopId;
    private String stopName;
    private Double longitude;
    private Double latitude;
    private Long companyId;
}