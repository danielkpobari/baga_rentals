package com.BagaBikes.Baga_bike_rent.model.dto;

import com.BagaBikes.Baga_bike_rent.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    private String username;
    private String brand;
    private Long bikeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
