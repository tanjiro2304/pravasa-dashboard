package com.example.application.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteFilter {
    @JsonProperty("depotIds")
    private List<Long> depotIds;

    @JsonProperty
    private List<Long> companyIds;

    @JsonProperty("companyId")
    private Long companyId;

    @JsonProperty("depotId")
    private Long depotId;

    @JsonProperty("routeId")
    private Long routeId;

    @JsonProperty("typeId")
    private Long typeId;

}