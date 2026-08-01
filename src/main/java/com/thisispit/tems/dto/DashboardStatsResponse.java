package com.thisispit.tems.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {

    private long totalUsers;
    private long totalRoutes;
    private long totalBookings;
    private long totalFeedbacks;
    private long confirmedBookings;
    private long cancelledBookings;
}