package com.example.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HaltDto {

    private Long id;

    private Integer position;

    private String stopName;

    private Long stopId;

    private Long routeId;

    private String routeNo;
}