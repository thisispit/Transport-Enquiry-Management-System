package com.thisispit.tems.service;

import com.thisispit.tems.dto.LoginRequest;
import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.dto.UserRegisterRequest;
import com.thisispit.tems.dto.UserResponse;
import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRegisterRequest request);

    UserResponse loginUser(LoginRequest request);

    UserResponse getUserById(Long userId);

    UserResponse addFavouriteRoute(Long userId, Long routeId);

    UserResponse removeFavouriteRoute(Long userId, Long routeId);

    List<RouteResponse> getFavouriteRoutes(Long userId);
}