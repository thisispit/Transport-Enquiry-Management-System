package com.thisispit.tems.service.impl;

import com.thisispit.tems.dto.LoginRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.dto.UserRegisterRequest;
import com.thisispit.tems.dto.UserResponse;
import com.thisispit.tems.dto.StopResponse;
import com.thisispit.tems.entity.Route;
import com.thisispit.tems.entity.Stop;
import com.thisispit.tems.entity.User;
import com.thisispit.tems.enums.UserRole;
import com.thisispit.tems.exception.BadRequestException;
import com.thisispit.tems.exception.DuplicateResourceException;
import com.thisispit.tems.exception.ResourceNotFoundException;
import com.thisispit.tems.repository.RouteRepository;
import com.thisispit.tems.repository.UserRepository;
import com.thisispit.tems.service.UserService;
import com.thisispit.tems.util.PasswordUtil;
import java.util.List;
import java.util.Comparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public UserServiceImpl(UserRepository userRepository, RouteRepository routeRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public UserResponse registerUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank() && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
        user.setRole(UserRole.USER);
        user.setActive(true);
        return toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new BadRequestException("User account is inactive");
        }
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        return toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        return toUserResponse(getUserEntity(userId));
    }

    @Override
    public UserResponse addFavouriteRoute(Long userId, Long routeId) {
        User user = getUserEntity(userId);
        Route route = getRouteEntity(routeId);
        user.getFavouriteRoutes().add(route);
        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse removeFavouriteRoute(Long userId, Long routeId) {
        User user = getUserEntity(userId);
        Route route = getRouteEntity(routeId);
        user.getFavouriteRoutes().remove(route);
        return toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteResponse> getFavouriteRoutes(Long userId) {
        User user = getUserEntity(userId);
        return user.getFavouriteRoutes().stream().map(this::toRouteResponse).toList();
    }

    private User getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Route getRouteEntity(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + routeId));
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getRole());
    }

    private RouteResponse toRouteResponse(Route route) {
        List<StopResponse> stops = route.getStops().stream()
                .sorted(Comparator.comparingInt(Stop::getStopOrder))
                .map(stop -> new StopResponse(stop.getId(), stop.getStopName(), stop.getStopOrder(), stop.getArrivalTime(), stop.getDepartureTime()))
                .toList();

        return new RouteResponse(route.getId(), route.getRouteName(), route.getSource(), route.getDestination(), route.getDistanceKm(),
                route.getTravelDurationMinutes(), route.getFare(), route.getTransportType(), route.getAvailableSeats(), route.isActive(), stops);
    }
}