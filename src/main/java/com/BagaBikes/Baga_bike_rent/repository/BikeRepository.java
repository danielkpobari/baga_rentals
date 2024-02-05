package com.BagaBikes.Baga_bike_rent.repository;

import com.BagaBikes.Baga_bike_rent.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

    Bike findByBrand(String brand);
}
