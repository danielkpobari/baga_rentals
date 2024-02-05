package com.BagaBikes.Baga_bike_rent.service;

import com.BagaBikes.Baga_bike_rent.exception.CustomException;
import com.BagaBikes.Baga_bike_rent.model.Bike;
import com.BagaBikes.Baga_bike_rent.model.Booking;
import com.BagaBikes.Baga_bike_rent.model.dto.BookingRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.BookingResponse;
import com.BagaBikes.Baga_bike_rent.repository.BikeRepository;
import com.BagaBikes.Baga_bike_rent.repository.BookingRepository;
import com.BagaBikes.Baga_bike_rent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    private final BikeRepository bikeRepository;

    private final UserRepository userRepository;


    public Booking bookBike(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserIdOrderByStartDateDesc(userId);
    }

    public ResponseEntity<BookingResponse> bookBike(BookingRequest bookingRequest) throws CustomException {
        if (bookingRequest.getStartDate().isAfter(bookingRequest.getEndDate())) {
            log.error("Invalid booking request: Start date should be before end date");
            return ResponseEntity.badRequest().body(new BookingResponse("Start date should be before end date"));
        }

        Bike bike = bikeRepository.findById(bookingRequest.getBikeId())
                .orElseThrow(() -> new CustomException("Bike not found"));

        if (isBikeAlreadyBooked(bike, bookingRequest.getStartDate(), bookingRequest.getEndDate())) {
            log.warn("Bike is already booked for the given time period");
            return ResponseEntity.badRequest().body(new BookingResponse("Bike is already booked for the given time period"));
        }

        Booking booking = new Booking();
        booking.setUser(userRepository.findByUsername(bookingRequest.getUsername()));
        booking.setBike(bike);
        bookingRepository.save(booking);

        log.info("Mr." + bookingRequest.getUsername() + "successfully booked a" + booking.getBike().getBrand() + " bike  Booking ID: {}", booking.getBookingId());

        BookingResponse bookingResponse = new BookingResponse("Mr." + bookingRequest.getUsername() + "successfully booked a" + booking.getBike().getBrand() + " bike");
        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setBrand(bike.getBrand());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setTotalCost(booking.getCost());
        return ResponseEntity.ok(bookingResponse);
    }

    private boolean isBikeAlreadyBooked(Bike bike, LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> bookings = bookingRepository.findByBikeId(bike.getId());
        for (Booking booking : bookings) {
            if ((startDate.isAfter(booking.getStartDate()) && startDate.isBefore(booking.getEndDate())) ||
                    (endDate.isAfter(booking.getStartDate()) && endDate.isBefore(booking.getEndDate())) ||
                    (startDate.isBefore(booking.getStartDate()) && endDate.isAfter(booking.getEndDate()))) {
                return true;
            }
        }
        return false;
    }

}