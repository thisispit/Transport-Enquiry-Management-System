package com.thisispit.tems.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be positive")
    private Long userId;

    @NotNull(message = "Route id is required")
    @Positive(message = "Route id must be positive")
    private Long routeId;

    @NotNull(message = "Travel date is required")
    private LocalDate travelDate;

    @NotNull(message = "Seat count is required")
    @Min(value = 1, message = "Seat count must be positive")
    private Integer seatCount;
}