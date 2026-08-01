package com.thisispit.tems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.thisispit.tems.dto.BookingRequest;
import com.thisispit.tems.dto.BookingResponse;
import com.thisispit.tems.entity.Booking;
import com.thisispit.tems.entity.Feedback;
import com.thisispit.tems.entity.Route;
import com.thisispit.tems.entity.User;
import com.thisispit.tems.enums.UserRole;
import com.thisispit.tems.repository.BookingRepository;
import com.thisispit.tems.repository.FeedbackRepository;
import com.thisispit.tems.repository.RouteRepository;
import com.thisispit.tems.repository.UserRepository;
import com.thisispit.tems.service.impl.BookingServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void createBookingShouldReduceSeatsAndReturnBooking() {
        User user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("hashed");
        user.setRole(UserRole.USER);
        user.setActive(true);

        Route route = new Route();
        route.setId(2L);
        route.setRouteName("City Express");
        route.setSource("Pune");
        route.setDestination("Mumbai");
        route.setFare(BigDecimal.valueOf(250));
        route.setAvailableSeats(10);
        route.setActive(true);

        BookingRequest request = new BookingRequest();
        request.setUserId(1L);
        request.setRouteId(2L);
        request.setTravelDate(LocalDate.now().plusDays(1));
        request.setSeatCount(2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(routeRepository.findById(2L)).thenReturn(Optional.of(route));
        when(routeRepository.save(any(Route.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId(100L);
            return booking;
        });

        BookingResponse response = bookingService.createBooking(request);

        assertEquals(100L, response.getId());
        assertEquals(8, route.getAvailableSeats());
        assertNotNull(response.getBookingReference());
        assertEquals(2, response.getSeatCount());
    }

    @Test
    void cancelBookingShouldRestoreSeats() {
        User user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("hashed");
        user.setRole(UserRole.USER);
        user.setActive(true);

        Route route = new Route();
        route.setId(2L);
        route.setRouteName("City Express");
        route.setFare(BigDecimal.valueOf(250));
        route.setAvailableSeats(8);
        route.setActive(true);

        Booking booking = new Booking();
        booking.setId(100L);
        booking.setUser(user);
        booking.setRoute(route);
        booking.setSeatCount(2);
        booking.setBookingReference("BK-TEST");
        booking.setStatus(com.thisispit.tems.enums.BookingStatus.CONFIRMED);

        when(bookingRepository.findById(100L)).thenReturn(Optional.of(booking));
        when(routeRepository.save(any(Route.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponse response = bookingService.cancelBooking(100L);

        assertEquals(com.thisispit.tems.enums.BookingStatus.CANCELLED, response.getStatus());
        assertEquals(10, route.getAvailableSeats());
    }
}