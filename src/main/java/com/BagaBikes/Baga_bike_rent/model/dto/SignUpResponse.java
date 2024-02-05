package com.BagaBikes.Baga_bike_rent.model.dto;

import lombok.Data;

@Data
public class SignUpResponse {
    private String message;
    public SignUpResponse(String message) {
        this.message = message;
    }

}
