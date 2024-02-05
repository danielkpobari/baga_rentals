package com.BagaBikes.Baga_bike_rent.service;

import com.BagaBikes.Baga_bike_rent.exception.BikeNotFoundException;
import com.BagaBikes.Baga_bike_rent.exception.CustomException;
import com.BagaBikes.Baga_bike_rent.model.Bike;
import com.BagaBikes.Baga_bike_rent.model.dto.BikeRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.BikeResponse;
import com.BagaBikes.Baga_bike_rent.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BikeService {
    private final BikeRepository bikeRepository;



//    public ResponseEntity<BikeResponse> addBike(BikeRequest request) throws CustomException {
//
//        log.info("Adding new bike - Model: {}, Brand: {}, Color: {}", request.getModel(), request.getBrand(), request.getColor());
//
//        Bike bike = new Bike();
//        bike.setModel(request.getModel());
//        bike.setBrand(request.getBrand());
//        bike.setColor(request.getColor());
//        bikeRepository.save(bike);
//
//        log.info("Bike added successfully - ID: {}", bike.getId());
//
//        BikeResponse addBikeResponse = new BikeResponse();
//        addBikeResponse.setId(bike.getId());
//        addBikeResponse.setMessage("Bike added successfully");
//
//        return ResponseEntity.ok(addBikeResponse);
//    }
public ResponseEntity<BikeResponse> addBike(BikeRequest request) throws CustomException {
    log.info("Adding new bike - Model: {}, Brand: {}, Color: {}", request.getModel(), request.getBrand(), request.getColor());

    Bike bike = new Bike();
    bike.setModel(request.getModel());
    bike.setBrand(request.getBrand());
    bike.setColor(request.getColor());

    // Save the bike and update its id
    Bike savedBike = bikeRepository.save(bike);
    bike.setId(savedBike.getId());

    log.info("Bike added successfully - ID: {}", bike.getId());

    BikeResponse addBikeResponse = new BikeResponse();
    addBikeResponse.setId(bike.getId());
    addBikeResponse.setMessage("Bike added successfully");

    return ResponseEntity.ok(addBikeResponse);
}



    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }


    public ResponseEntity<BikeResponse> updateBike(Bike bike, Long id) throws CustomException {
        log.info("Updating bike with ID: {}", id);

        Bike existingBike = bikeRepository.findById(id)
                .orElseThrow(() -> new BikeNotFoundException("Bike with ID " + id + " not found"));

        existingBike.setModel(bike.getModel());
        existingBike.setBrand(bike.getBrand());
        existingBike.setColor(bike.getColor());
        bikeRepository.save(existingBike);

        log.info("Bike updated successfully - ID: {}", existingBike.getId());

        BikeResponse updateBikeResponse = new BikeResponse();
        updateBikeResponse.setId(existingBike.getId());
        updateBikeResponse.setMessage("Bike updated successfully");
        return ResponseEntity.ok(updateBikeResponse);
    }

  public ResponseEntity<BikeResponse> deleteBike(Long id) {
        Bike bike = bikeRepository.findById(id).orElse(null);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        bikeRepository.delete(bike);
        BikeResponse deleteBikeResponse = new BikeResponse();
        deleteBikeResponse.setId(bike.getId());
        deleteBikeResponse.setMessage("Bike deleted successfully");
        return ResponseEntity.ok(deleteBikeResponse);
    }
    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id).orElse(null);
    }
    public Bike getBikeByBrand(String brand) {
        return bikeRepository.findByBrand(brand);
    }
}
