package com.centros_sass.app.dto.transport;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportRouteUpdateDTO {

    private Integer routeNumber;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer routeShiftId;

    private Integer routeVehicleId;

    private Integer driverId;

    private Integer copilotId;

    private Boolean isActive;

}
