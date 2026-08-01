package com.thisispit.tems.controller;

import com.thisispit.tems.dto.RouteRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.dto.StopRequest;
import com.thisispit.tems.dto.StopResponse;
import com.thisispit.tems.service.RouteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public Page<RouteResponse> getAllRoutes(Pageable pageable) {
        return routeService.getAllRoutes(pageable);
    }

    @GetMapping("/{routeId}")
    public RouteResponse getRouteById(@PathVariable Long routeId) {
        return routeService.getRouteById(routeId);
    }

    @GetMapping("/search")
    public Page<RouteResponse> searchRoutes(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            Pageable pageable) {
        return routeService.searchRoutes(source, destination, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponse createRoute(@Valid @RequestBody RouteRequest request) {
        return routeService.createRoute(request);
    }

    @PutMapping("/{routeId}")
    public RouteResponse updateRoute(@PathVariable Long routeId, @Valid @RequestBody RouteRequest request) {
        return routeService.updateRoute(routeId, request);
    }

    @DeleteMapping("/{routeId}")
    public void deleteRoute(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
    }

    @GetMapping("/{routeId}/stops")
    public List<StopResponse> getStopsByRoute(@PathVariable Long routeId) {
        return routeService.getStopsByRoute(routeId);
    }

    @PostMapping("/{routeId}/stops")
    public StopResponse addStop(@PathVariable Long routeId, @Valid @RequestBody StopRequest request) {
        return routeService.addStop(routeId, request);
    }

    @PutMapping("/{routeId}/stops/{stopId}")
    public StopResponse updateStop(@PathVariable Long routeId, @PathVariable Long stopId, @Valid @RequestBody StopRequest request) {
        return routeService.updateStop(routeId, stopId, request);
    }

    @DeleteMapping("/{routeId}/stops/{stopId}")
    public void deleteStop(@PathVariable Long routeId, @PathVariable Long stopId) {
        routeService.deleteStop(routeId, stopId);
    }
}