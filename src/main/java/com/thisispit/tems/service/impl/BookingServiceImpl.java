package com.thisispit.tems.service.impl;

import com.thisispit.tems.dto.BookingRequest;
import com.thisispit.tems.dto.BookingResponse;
import com.thisispit.tems.dto.DashboardStatsResponse;
import com.thisispit.tems.dto.FeedbackRequest;
import com.thisispit.tems.dto.FeedbackResponse;
import com.thisispit.tems.entity.Booking;
import com.thisispit.tems.entity.Feedback;
import com.thisispit.tems.entity.Route;
import com.thisispit.tems.entity.User;
import com.thisispit.tems.enums.BookingStatus;
import com.thisispit.tems.exception.BadRequestException;
import com.thisispit.tems.exception.ResourceNotFoundException;
import com.thisispit.tems.repository.BookingRepository;
import com.thisispit.tems.repository.FeedbackRepository;
import com.thisispit.tems.repository.RouteRepository;
import com.thisispit.tems.repository.UserRepository;
import com.thisispit.tems.service.BookingService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, FeedbackRepository feedbackRepository, UserRepository userRepository,
            RouteRepository routeRepository) {
        this.bookingRepository = bookingRepository;
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        User user = getUser(request.getUserId());
        Route route = getRoute(request.getRouteId());

        if (!route.isActive()) {
            throw new BadRequestException("Route is inactive");
        }
        if (route.getAvailableSeats() < request.getSeatCount()) {
            throw new BadRequestException("Not enough seats available");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoute(route);
        booking.setBookingDate(LocalDateTime.now());
        booking.setTravelDate(request.getTravelDate());
        booking.setSeatCount(request.getSeatCount());
        booking.setTotalAmount(route.getFare().multiply(BigDecimal.valueOf(request.getSeatCount())));
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingReference(generateBookingReference());

        route.setAvailableSeats(route.getAvailableSeats() - request.getSeatCount());
        routeRepository.save(route);

        return toBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = getBooking(bookingId);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        Route route = booking.getRoute();
        route.setAvailableSeats(route.getAvailableSeats() + booking.getSeatCount());
        routeRepository.save(route);
        return toBookingResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long bookingId) {
        return toBookingResponse(getBooking(bookingId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingHistoryByUserId(Long userId) {
        getUser(userId);
        return bookingRepository.findByUserIdOrderByBookingDateDesc(userId).stream().map(this::toBookingResponse).toList();
    }

    @Override
    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        User user = getUser(request.getUserId());
        Route route = request.getRouteId() != null ? getRoute(request.getRouteId()) : null;

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setRoute(route);
        feedback.setRating(request.getRating());
        feedback.setComments(request.getComments());
        return toFeedbackResponse(feedbackRepository.save(feedback));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> getFeedbackByRoute(Long routeId) {
        getRoute(routeId);
        return feedbackRepository.findByRouteIdOrderByCreatedAtDesc(routeId).stream().map(this::toFeedbackResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsResponse getDashboardStats() {
        return new DashboardStatsResponse(
                userRepository.count(),
                routeRepository.countByActiveTrue(),
                bookingRepository.count(),
                feedbackRepository.count(),
                bookingRepository.countByStatus(BookingStatus.CONFIRMED),
                bookingRepository.countByStatus(BookingStatus.CANCELLED));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookingResponse> getAllBookings(Pageable pageable) {
        return bookingRepository.findAllByOrderByBookingDateDesc(pageable).map(this::toBookingResponse);
    }

    private Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    private Route getRoute(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + routeId));
    }

    private String generateBookingReference() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getBookingReference(),
                booking.getUser().getId(),
                booking.getUser().getFullName(),
                booking.getRoute().getId(),
                booking.getRoute().getRouteName(),
                booking.getRoute().getSource(),
                booking.getRoute().getDestination(),
                booking.getTravelDate(),
                booking.getBookingDate(),
                booking.getSeatCount(),
                booking.getTotalAmount(),
                booking.getStatus());
    }

    private FeedbackResponse toFeedbackResponse(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getUser().getId(),
                feedback.getUser().getFullName(),
                feedback.getRoute() != null ? feedback.getRoute().getId() : null,
                feedback.getRoute() != null ? feedback.getRoute().getRouteName() : null,
                feedback.getRating(),
                feedback.getComments(),
                feedback.getCreatedAt());
    }
}