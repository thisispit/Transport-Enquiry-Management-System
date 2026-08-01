package com.thisispit.tems.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.thisispit.tems.dto.RouteResponse;
import com.thisispit.tems.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RouteController.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RouteService routeService;

    @Test
    void getAllRoutesShouldReturnOk() throws Exception {
        Page<RouteResponse> page = new PageImpl<>(java.util.List.of());
        when(routeService.getAllRoutes(any())).thenReturn(page);

        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
          .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    void createRouteShouldReturnCreated() throws Exception {
        RouteResponse response = new RouteResponse();
        response.setId(1L);
        response.setRouteName("City Express");
        response.setSource("Pune");
        response.setDestination("Mumbai");
        response.setDistanceKm(150.0);
        response.setTravelDurationMinutes(180);
        response.setFare(BigDecimal.valueOf(250));
        response.setTransportType("Bus");
        response.setAvailableSeats(40);
        response.setActive(true);
        response.setStops(java.util.List.of());

        when(routeService.createRoute(any())).thenReturn(response);

        String requestJson = """
                {
                  "routeName": "City Express",
                  "source": "Pune",
                  "destination": "Mumbai",
                  "distanceKm": 150.0,
                  "travelDurationMinutes": 180,
                  "fare": 250,
                  "transportType": "Bus",
                  "availableSeats": 40
                }
                """;

        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
          .andExpect(jsonPath("$.routeName").value("City Express"))
          .andExpect(jsonPath("$.source").value("Pune"));
    }
}