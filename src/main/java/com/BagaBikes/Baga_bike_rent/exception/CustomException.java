package com.BagaBikes.Baga_bike_rent.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
