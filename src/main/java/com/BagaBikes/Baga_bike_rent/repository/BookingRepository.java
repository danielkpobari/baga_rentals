package com.BagaBikes.Baga_bike_rent.repository;

import com.BagaBikes.Baga_bike_rent.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<Booking> findByUserIdOrderByStartDateDesc(Long userId);

    List<Booking> findByBikeId(Long id);
}
