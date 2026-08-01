package com.thisispit.tems.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {

    private Long id;
    private String routeName;
    private String source;
    private String destination;
    private double distanceKm;
    private int travelDurationMinutes;
    private BigDecimal fare;
    private String transportType;
    private int availableSeats;
    private boolean active;
    private List<StopResponse> stops;
}