package com.thisispit.tems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopResponse {

    private Long id;
    private String stopName;
    private int stopOrder;
    private String arrivalTime;
    private String departureTime;
}