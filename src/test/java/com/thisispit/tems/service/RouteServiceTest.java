package com.thisispit.tems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thisispit.tems.dto.RouteRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.entity.Route;
import com.thisispit.tems.repository.StopRepository;
import com.thisispit.tems.repository.RouteRepository;
import com.thisispit.tems.service.impl.RouteServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private StopRepository stopRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @Test
    void createRouteShouldSaveAndReturnRoute() {
        RouteRequest request = new RouteRequest();
        request.setRouteName("City Express");
        request.setSource("Pune");
        request.setDestination("Mumbai");
        request.setDistanceKm(150.0);
        request.setTravelDurationMinutes(180);
        request.setFare(BigDecimal.valueOf(250));
        request.setTransportType("Bus");
        request.setAvailableSeats(40);

        when(routeRepository.save(any(Route.class))).thenAnswer(invocation -> {
            Route route = invocation.getArgument(0);
            route.setId(1L);
            return route;
        });

        RouteResponse response = routeService.createRoute(request);

        assertEquals(1L, response.getId());
        assertEquals("City Express", response.getRouteName());
        assertTrue(response.isActive());
        verify(routeRepository).save(any(Route.class));
    }

    @Test
    void deleteRouteShouldDeactivateRoute() {
        Route route = new Route();
        route.setId(1L);
        route.setActive(true);

        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));
        when(routeRepository.save(any(Route.class))).thenAnswer(invocation -> invocation.getArgument(0));

        routeService.deleteRoute(1L);

        assertFalse(route.isActive());
        verify(routeRepository).save(route);
    }
}