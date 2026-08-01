package com.thisispit.tems.service;

import com.thisispit.tems.dto.BookingRequest;
import com.thisispit.tems.dto.BookingResponse;
import com.thisispit.tems.dto.DashboardStatsResponse;
import com.thisispit.tems.dto.FeedbackRequest;
import com.thisispit.tems.dto.FeedbackResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    BookingResponse cancelBooking(Long bookingId);

    BookingResponse getBookingById(Long bookingId);

    List<BookingResponse> getBookingHistoryByUserId(Long userId);

    FeedbackResponse submitFeedback(FeedbackRequest request);

    List<FeedbackResponse> getFeedbackByRoute(Long routeId);

    DashboardStatsResponse getDashboardStats();

    Page<BookingResponse> getAllBookings(Pageable pageable);
}