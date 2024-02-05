package com.BagaBikes.Baga_bike_rent.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BikeNotFoundException extends RuntimeException{
    public BikeNotFoundException(String message) {
        super(message);
    }
}
