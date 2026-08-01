package com.thisispit.tems.repository;

import com.thisispit.tems.entity.Booking;
import com.thisispit.tems.enums.BookingStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);

    Page<Booking> findAllByOrderByBookingDateDesc(Pageable pageable);

    Optional<Booking> findByBookingReference(String bookingReference);

    long countByStatus(BookingStatus status);
}