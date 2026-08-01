package com.thisispit.tems.dto;

import com.thisispit.tems.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private String bookingReference;
    private Long userId;
    private String userName;
    private Long routeId;
    private String routeName;
    private String source;
    private String destination;
    private LocalDate travelDate;
    private LocalDateTime bookingDate;
    private int seatCount;
    private BigDecimal totalAmount;
    private BookingStatus status;
}