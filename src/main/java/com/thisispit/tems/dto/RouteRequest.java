package com.thisispit.tems.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteRequest {

    @NotBlank(message = "Route name is required")
    private String routeName;

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be positive")
    private Double distanceKm;

    @NotNull(message = "Travel duration is required")
    @Min(value = 1, message = "Travel duration must be positive")
    private Integer travelDurationMinutes;

    @NotNull(message = "Fare is required")
    @Positive(message = "Fare must be positive")
    private BigDecimal fare;

    @NotBlank(message = "Transport type is required")
    private String transportType;

    @NotNull(message = "Available seats are required")
    @Positive(message = "Available seats must be positive")
    private Integer availableSeats;
}