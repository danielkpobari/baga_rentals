package com.BagaBikes.Baga_bike_rent.controller;

import com.BagaBikes.Baga_bike_rent.model.Bike;
import com.BagaBikes.Baga_bike_rent.model.dto.BikeRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.BikeResponse;
import com.BagaBikes.Baga_bike_rent.model.dto.BookingRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.BookingResponse;
import com.BagaBikes.Baga_bike_rent.service.BikeService;
import com.BagaBikes.Baga_bike_rent.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bikes")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService bikeService;
    private final BookingService bookingService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addBike")
    public ResponseEntity<BikeResponse> addBike(@Valid @RequestBody BikeRequest request) {
        return bikeService.addBike(request);
    }

    @PostMapping("/bookBike")
    public ResponseEntity<BookingResponse> bookBike(@Valid @RequestBody BookingRequest request) {
        return bookingService.bookBike(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllBikes")
    public List<Bike> getAllBikes() {
        return bikeService.getAllBikes();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/viewUserBookings/{username}")
    public ResponseEntity<?> viewUserBookings(@PathVariable String username) {
        return bookingService.viewUserBookings(username);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/viewAvailableUnbookedBikes")
    public ResponseEntity<?> viewAvailableUnbookedBikes() {
        return bikeService.viewAvailableUnBookedBikes();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/viewBookedBikes")
    public ResponseEntity<?> viewAllBookings() {
        return bookingService.viewAllBookings();
    }
}
