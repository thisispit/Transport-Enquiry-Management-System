package com.thisispit.tems.service.impl;

import com.thisispit.tems.dto.RouteRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.dto.StopRequest;
import com.thisispit.tems.dto.StopResponse;
import com.thisispit.tems.entity.Route;
import com.thisispit.tems.entity.Stop;
import com.thisispit.tems.exception.BadRequestException;
import com.thisispit.tems.exception.DuplicateResourceException;
import com.thisispit.tems.exception.ResourceNotFoundException;
import com.thisispit.tems.repository.RouteRepository;
import com.thisispit.tems.repository.StopRepository;
import com.thisispit.tems.service.RouteService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    public RouteServiceImpl(RouteRepository routeRepository, StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    @Override
    public RouteResponse createRoute(RouteRequest request) {
        Route route = new Route();
        applyRequest(route, request);
        route.setActive(true);
        return toRouteResponse(routeRepository.save(route));
    }

    @Override
    public RouteResponse updateRoute(Long routeId, RouteRequest request) {
        Route route = getRouteEntity(routeId);
        applyRequest(route, request);
        return toRouteResponse(routeRepository.save(route));
    }

    @Override
    public void deleteRoute(Long routeId) {
        Route route = getRouteEntity(routeId);
        route.setActive(false);
        routeRepository.save(route);
    }

    @Override
    @Transactional(readOnly = true)
    public RouteResponse getRouteById(Long routeId) {
        return toRouteResponse(getRouteEntity(routeId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RouteResponse> getAllRoutes(Pageable pageable) {
        return routeRepository.findByActiveTrue(pageable).map(this::toRouteResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RouteResponse> searchRoutes(String source, String destination, Pageable pageable) {
        String safeSource = source == null ? "" : source;
        String safeDestination = destination == null ? "" : destination;
        return routeRepository
                .findBySourceContainingIgnoreCaseAndDestinationContainingIgnoreCaseAndActiveTrue(safeSource, safeDestination, pageable)
            .map(this::toRouteResponse);
    }

    private void applyRequest(Route route, RouteRequest request) {
        if (request.getDistanceKm() <= 0 || request.getTravelDurationMinutes() <= 0 || request.getAvailableSeats() <= 0) {
            throw new BadRequestException("Numeric route values must be positive");
        }
        route.setRouteName(request.getRouteName());
        route.setSource(request.getSource());
        route.setDestination(request.getDestination());
        route.setDistanceKm(request.getDistanceKm());
        route.setTravelDurationMinutes(request.getTravelDurationMinutes());
        route.setFare(request.getFare());
        route.setTransportType(request.getTransportType());
        route.setAvailableSeats(request.getAvailableSeats());
    }

    private Route getRouteEntity(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + routeId));
    }

    @Override
    public StopResponse addStop(Long routeId, StopRequest request) {
        Route route = getRouteEntity(routeId);
        validateUniqueStopOrder(routeId, request.getStopOrder(), null);

        Stop stop = new Stop();
        stop.setRoute(route);
        applyRequest(stop, request);
        return toStopResponse(stopRepository.save(stop));
    }

    @Override
    public StopResponse updateStop(Long routeId, Long stopId, StopRequest request) {
        Stop stop = stopRepository.findByIdAndRouteId(stopId, routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Stop not found with id: " + stopId));
        validateUniqueStopOrder(routeId, request.getStopOrder(), stopId);
        applyRequest(stop, request);
        return toStopResponse(stopRepository.save(stop));
    }

    @Override
    public void deleteStop(Long routeId, Long stopId) {
        Stop stop = stopRepository.findByIdAndRouteId(stopId, routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Stop not found with id: " + stopId));
        stopRepository.delete(stop);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StopResponse> getStopsByRoute(Long routeId) {
        getRouteEntity(routeId);
        return stopRepository.findByRouteIdOrderByStopOrderAsc(routeId).stream().map(this::toStopResponse).toList();
    }

    private void applyRequest(Stop stop, StopRequest request) {
        stop.setStopName(request.getStopName());
        stop.setStopOrder(request.getStopOrder());
        stop.setArrivalTime(request.getArrivalTime());
        stop.setDepartureTime(request.getDepartureTime());
    }

    private void validateUniqueStopOrder(Long routeId, Integer stopOrder, Long stopId) {
        stopRepository.findByRouteIdAndStopOrder(routeId, stopOrder).ifPresent(existingStop -> {
            if (stopId == null || !existingStop.getId().equals(stopId)) {
                throw new DuplicateResourceException("Stop order already exists for this route");
            }
        });
    }

    private RouteResponse toRouteResponse(Route route) {
        List<StopResponse> stops = route.getStops().stream()
                .sorted(Comparator.comparingInt(Stop::getStopOrder))
                .map(this::toStopResponse)
                .toList();

        return new RouteResponse(route.getId(), route.getRouteName(), route.getSource(), route.getDestination(), route.getDistanceKm(),
                route.getTravelDurationMinutes(), route.getFare(), route.getTransportType(), route.getAvailableSeats(), route.isActive(), stops);
    }

    private StopResponse toStopResponse(Stop stop) {
        return new StopResponse(stop.getId(), stop.getStopName(), stop.getStopOrder(), stop.getArrivalTime(), stop.getDepartureTime());
    }
}