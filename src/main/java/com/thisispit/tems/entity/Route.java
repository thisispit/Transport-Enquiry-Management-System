package com.thisispit.tems.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "routes")
public class Route extends BaseEntity {

    @Column(nullable = false)
    private String routeName;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private double distanceKm;

    @Column(nullable = false)
    private int travelDurationMinutes;

    @Column(nullable = false)
    private BigDecimal fare;

    @Column(nullable = false)
    private String transportType;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<Stop> stops = new ArrayList<>();

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<Feedback> feedbacks = new ArrayList<>();

    @ManyToMany(mappedBy = "favouriteRoutes", fetch = FetchType.LAZY)
    private Set<User> favouritedByUsers;
}