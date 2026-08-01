package com.thisispit.tems.config;

import com.thisispit.tems.entity.Route;
import com.thisispit.tems.entity.Stop;
import com.thisispit.tems.repository.RouteRepository;
import com.thisispit.tems.repository.StopRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedRoutes(RouteRepository routeRepository, StopRepository stopRepository) {
        return args -> {
            if (routeRepository.count() > 0) {
                return;
            }

            List<RouteSeed> seeds = List.of(
                    new RouteSeed("Intercity Express", "Pune", "Mumbai", 150.0, 180, new BigDecimal("250.00"), "AC Bus", 40,
                            List.of("Hinjewadi", "Swargate", "Panvel")),
                    new RouteSeed("Metro Link", "Delhi", "Noida", 24.0, 45, new BigDecimal("80.00"), "City Bus", 50,
                            List.of("Connaught Place", "Akshardham", "Sector 62")),
                    new RouteSeed("Capital Connector", "Delhi", "Jaipur", 280.0, 330, new BigDecimal("450.00"), "AC Coach", 38,
                            List.of("Gurugram", "Neemrana", "Behror")),
                    new RouteSeed("Harbour Line", "Mumbai", "Pune", 150.0, 190, new BigDecimal("260.00"), "AC Bus", 42,
                            List.of("Navi Mumbai", "Lonavala", "Dehu Road")),
                    new RouteSeed("South Gate", "Bengaluru", "Mysuru", 145.0, 210, new BigDecimal("220.00"), "Non-AC Bus", 45,
                            List.of("Ramanagara", "Mandya", "Srirangapatna")),
                    new RouteSeed("Coastal Route", "Chennai", "Pondicherry", 160.0, 240, new BigDecimal("180.00"), "Mini Bus", 28,
                            List.of("Mahabalipuram", "Kalpakkam", "Auroville")),
                    new RouteSeed("Tech Corridor", "Hyderabad", "Warangal", 145.0, 180, new BigDecimal("190.00"), "Express Bus", 40,
                            List.of("LB Nagar", "Nalgonda", "Kazipet")),
                    new RouteSeed("Kolkata Link", "Kolkata", "Durgapur", 170.0, 210, new BigDecimal("210.00"), "AC Bus", 36,
                            List.of("Bardhaman", "Asansol", "Dhanbad"))
            );

            for (RouteSeed seed : seeds) {
                Route route = new Route();
                route.setRouteName(seed.routeName);
                route.setSource(seed.source);
                route.setDestination(seed.destination);
                route.setDistanceKm(seed.distanceKm);
                route.setTravelDurationMinutes(seed.travelDurationMinutes);
                route.setFare(seed.fare);
                route.setTransportType(seed.transportType);
                route.setAvailableSeats(seed.availableSeats);
                route.setActive(true);

                Route savedRoute = routeRepository.save(route);

                List<Stop> stops = new ArrayList<>();
                for (int index = 0; index < seed.stops.size(); index++) {
                    Stop stop = new Stop();
                    stop.setRoute(savedRoute);
                    stop.setStopName(seed.stops.get(index));
                    stop.setStopOrder(index + 1);
                    stop.setArrivalTime(null);
                    stop.setDepartureTime(null);
                    stops.add(stop);
                }
                stopRepository.saveAll(stops);
            }
        };
    }

    private static class RouteSeed {
        private final String routeName;
        private final String source;
        private final String destination;
        private final double distanceKm;
        private final int travelDurationMinutes;
        private final BigDecimal fare;
        private final String transportType;
        private final int availableSeats;
        private final List<String> stops;

        private RouteSeed(String routeName, String source, String destination, double distanceKm, int travelDurationMinutes,
                BigDecimal fare, String transportType, int availableSeats, List<String> stops) {
            this.routeName = routeName;
            this.source = source;
            this.destination = destination;
            this.distanceKm = distanceKm;
            this.travelDurationMinutes = travelDurationMinutes;
            this.fare = fare;
            this.transportType = transportType;
            this.availableSeats = availableSeats;
            this.stops = stops;
        }
    }
}