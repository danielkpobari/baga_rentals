package com.BagaBikes.Baga_bike_rent.service;

import com.BagaBikes.Baga_bike_rent.model.Bike;
import com.BagaBikes.Baga_bike_rent.model.dto.BikeRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.BikeResponse;
import com.BagaBikes.Baga_bike_rent.repository.BikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BikeServiceTest {

    @Mock
    private BikeRepository bikeRepository;

    @InjectMocks
    private BikeService bikeService;

    @Test
    public void testAddBike() {
        // Given
        BikeRequest request = new BikeRequest();
        request.setModel("2023");
        request.setBrand("Toyota");
        request.setColor("Red");

        Bike savedBike = new Bike();
        savedBike.setId(1L);
        savedBike.setModel(request.getModel());
        savedBike.setBrand(request.getBrand());
        savedBike.setColor(request.getColor());

        when(bikeRepository.save(any(Bike.class))).thenReturn(savedBike);

        // When
        ResponseEntity<BikeResponse> responseEntity = bikeService.addBike(request);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());

        BikeResponse responseBody = responseEntity.getBody();
        assertEquals(1L, responseBody.getId());
        assertEquals("Bike added successfully", responseBody.getMessage());
    }
}