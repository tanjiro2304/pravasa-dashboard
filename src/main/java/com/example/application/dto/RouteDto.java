package com.example.application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDto {

    private Long routeId;

    private String routeNo;

    private String source;

    private String destination;

    private Boolean isAirConditioned;

    private String routeType;

    private String depotName;

    private Double fare;

    private Double distance;

    private Long routeTypeId;



//    public RouteDto(Long routeId, String routeNo, String source, String destination, Boolean isAirConditioned, String routeType, String depotName, Double fare, Double distance, Boolean isAirCondition) {
//        this.routeId = routeId;
//        this.routeNo = routeNo;
//        this.source = source;
//        this.destination = destination;
//        this.isAirConditioned = isAirConditioned;
//        this.routeType = routeType;
//        this.depotName = depotName;
//        this.fare = fare;
//        this.distance = distance;
//        this.isAirCondition = isAirCondition;
//    }

//    public Long getRouteId() {
//        return routeId;
//    }
//
//    public void setRouteId(Long routeId) {
//        this.routeId = routeId;
//    }
//
//    public String getRouteNo() {
//        return routeNo;
//    }
//
//    public void setRouteNo(String routeNo) {
//        this.routeNo = routeNo;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }
//
//    public Boolean getAirConditioned() {
//        return isAirConditioned;
//    }
//
//    public void setAirConditioned(Boolean airConditioned) {
//        isAirConditioned = airConditioned;
//    }
//
//    public String getRouteType() {
//        return routeType;
//    }
//
//    public void setRouteType(String routeType) {
//        this.routeType = routeType;
//    }
//
//    public String getDepotName() {
//        return depotName;
//    }
//
//    public void setDepotName(String depotName) {
//        this.depotName = depotName;
//    }
//
//    public Double getFare() {
//        return fare;
//    }
//
//    public void setFare(Double fare) {
//        this.fare = fare;
//    }
//
//    public Double getDistance() {
//        return distance;
//    }
//
//    public void setDistance(Double distance) {
//        this.distance = distance;
//    }
//
//    public Boolean getAirCondition() {
//        return isAirCondition;
//    }
//
//    public void setAirCondition(Boolean airCondition) {
//        isAirCondition = airCondition;
//    }
}