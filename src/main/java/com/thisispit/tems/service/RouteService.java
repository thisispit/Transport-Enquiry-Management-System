package com.thisispit.tems.service;

import com.thisispit.tems.dto.RouteRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.dto.StopRequest;
import com.thisispit.tems.dto.StopResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RouteService {

    RouteResponse createRoute(RouteRequest request);

    RouteResponse updateRoute(Long routeId, RouteRequest request);

    void deleteRoute(Long routeId);

    RouteResponse getRouteById(Long routeId);

    Page<RouteResponse> getAllRoutes(Pageable pageable);

    Page<RouteResponse> searchRoutes(String source, String destination, Pageable pageable);

    StopResponse addStop(Long routeId, StopRequest request);

    StopResponse updateStop(Long routeId, Long stopId, StopRequest request);

    void deleteStop(Long routeId, Long stopId);

    List<StopResponse> getStopsByRoute(Long routeId);
}