package com.thisispit.tems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StopRequest {

    @NotBlank(message = "Stop name is required")
    private String stopName;

    @NotNull(message = "Stop order is required")
    @Positive(message = "Stop order must be positive")
    private Integer stopOrder;

    private String arrivalTime;
    private String departureTime;
}