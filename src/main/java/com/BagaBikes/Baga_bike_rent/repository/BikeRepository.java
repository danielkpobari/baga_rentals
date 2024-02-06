package com.BagaBikes.Baga_bike_rent.repository;

import com.BagaBikes.Baga_bike_rent.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

    Bike findByBrand(String brand);
    @Query("SELECT b FROM Bike b WHERE b NOT IN (SELECT bb.bike FROM Booking bb)")
    List<Bike> findAvailableUnBookedBikes();
}
