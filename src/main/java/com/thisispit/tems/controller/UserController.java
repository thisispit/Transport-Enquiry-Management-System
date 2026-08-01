package com.thisispit.tems.controller;

import com.thisispit.tems.dto.LoginRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.dto.UserRegisterRequest;
import com.thisispit.tems.dto.UserResponse;
import com.thisispit.tems.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/{userId}/favourites/{routeId}")
    public UserResponse addFavouriteRoute(@PathVariable Long userId, @PathVariable Long routeId) {
        return userService.addFavouriteRoute(userId, routeId);
    }

    @DeleteMapping("/{userId}/favourites/{routeId}")
    public UserResponse removeFavouriteRoute(@PathVariable Long userId, @PathVariable Long routeId) {
        return userService.removeFavouriteRoute(userId, routeId);
    }

    @GetMapping("/{userId}/favourites")
    public List<RouteResponse> getFavouriteRoutes(@PathVariable Long userId) {
        return userService.getFavouriteRoutes(userId);
    }
}