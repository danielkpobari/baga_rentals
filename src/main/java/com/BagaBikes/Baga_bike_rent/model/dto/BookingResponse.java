package com.BagaBikes.Baga_bike_rent.model.dto;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private String username;
    private String brand;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long bookingId;
    private String status;
    private Long totalCost;
    private String message;

    public BookingResponse(String s) {
        this.message = s;
    }
}
