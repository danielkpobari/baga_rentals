package com.BagaBikes.Baga_bike_rent.model.dto;

import com.BagaBikes.Baga_bike_rent.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank(message = "username must not be blank")
    private String username;

    @NotBlank(message = "password must not be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{6,}$", message = "password must be at least 6 characters and contain a capital letter, a lowercase letter, a number, and a special character")
    private String password;

    @NotBlank(message = "email must not be blank")
    @Email(message = "please enter a valid email")
    private String email;

    @NotBlank(message = "role must not be blank")
    private Set<Role> roles;
}

