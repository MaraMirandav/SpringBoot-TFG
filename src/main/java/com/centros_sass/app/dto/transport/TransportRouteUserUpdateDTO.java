package com.centros_sass.app.dto.transport;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportRouteUserUpdateDTO {

    private LocalTime arrivalTime;

    private Boolean usesWheelchair;

    private String comment;

}
