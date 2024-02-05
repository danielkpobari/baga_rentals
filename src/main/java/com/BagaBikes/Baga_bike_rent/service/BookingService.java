package com.BagaBikes.Baga_bike_rent.service;

import com.BagaBikes.Baga_bike_rent.exception.CustomException;
import com.BagaBikes.Baga_bike_rent.model.Bike;
import com.BagaBikes.Baga_bike_rent.model.Booking;
import com.BagaBikes.Baga_bike_rent.model.User;
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
        User user = userRepository.findByUsername(bookingRequest.getUsername());
        booking.setUser(user);
        booking.setBike(bike);
        booking.setStartDate(bookingRequest.getStartDate());
        booking.setEndDate(bookingRequest.getEndDate());
        bookingRepository.save(booking);

        log.info("Mr. {} successfully booked a {} bike. Booking ID: {}", user.getUsername(), bike.getBrand(), booking.getBookingId());

        BookingResponse bookingResponse = new BookingResponse("Mr. " + user.getUsername() + " successfully booked a " + bike.getBrand() + " bike");
        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setBrand(bike.getBrand());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setUsername(user.getUsername());
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

    public ResponseEntity<BookingResponse> cancelBooking(Long bookingId) throws CustomException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException("Booking not found"));

        bookingRepository.delete(booking);

        log.info("Booking with ID: {} has been cancelled", bookingId);

        BookingResponse bookingResponse = new BookingResponse("Booking with ID: " + bookingId + " has been cancelled");
        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setBrand(booking.getBike().getBrand());
        bookingResponse.setStartDate(booking.getStartDate());
        bookingResponse.setUsername(booking.getUser().getUsername());
        bookingResponse.setEndDate(booking.getEndDate());
        bookingResponse.setTotalCost(booking.getCost());

        return ResponseEntity.ok(bookingResponse);
    }

    public ResponseEntity<?> viewUserBookings(String username) {
        User user = userRepository.findByUsername(username);
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());

        return ResponseEntity.ok(bookings);
    }

}