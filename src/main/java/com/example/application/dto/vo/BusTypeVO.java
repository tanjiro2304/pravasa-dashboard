package com.example.application.dto.vo;

import com.example.application.dto.BusDto;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusTypeVO {
    private BusDto busType;
    private Integer noOfBuses;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BusTypeVO busTypeVO = (BusTypeVO) o;
        return Objects.equals(busType, busTypeVO.busType) && Objects.equals(noOfBuses, busTypeVO.noOfBuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busType, noOfBuses);
    }
}
