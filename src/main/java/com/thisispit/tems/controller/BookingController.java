package com.thisispit.tems.controller;

import com.thisispit.tems.dto.BookingRequest;
import com.thisispit.tems.dto.BookingResponse;
import com.thisispit.tems.dto.DashboardStatsResponse;
import com.thisispit.tems.dto.FeedbackRequest;
import com.thisispit.tems.dto.FeedbackResponse;
import com.thisispit.tems.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookings")
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @DeleteMapping("/bookings/{bookingId}")
    public BookingResponse cancelBooking(@PathVariable Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @GetMapping("/bookings/{bookingId}")
    public BookingResponse getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @GetMapping("/bookings/user/{userId}")
    public List<BookingResponse> getBookingHistory(@PathVariable Long userId) {
        return bookingService.getBookingHistoryByUserId(userId);
    }

    @PostMapping("/feedbacks")
    public FeedbackResponse submitFeedback(@Valid @RequestBody FeedbackRequest request) {
        return bookingService.submitFeedback(request);
    }

    @GetMapping("/feedbacks/route/{routeId}")
    public List<FeedbackResponse> getFeedbackByRoute(@PathVariable Long routeId) {
        return bookingService.getFeedbackByRoute(routeId);
    }

    @GetMapping("/admin/dashboard")
    public DashboardStatsResponse getDashboardStats() {
        return bookingService.getDashboardStats();
    }

    @GetMapping("/admin/bookings")
    public org.springframework.data.domain.Page<BookingResponse> getAllBookings(org.springframework.data.domain.Pageable pageable) {
        return bookingService.getAllBookings(pageable);
    }
}