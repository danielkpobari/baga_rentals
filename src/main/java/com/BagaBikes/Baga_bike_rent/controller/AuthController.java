package com.BagaBikes.Baga_bike_rent.controller;

import com.BagaBikes.Baga_bike_rent.configuration.JwtResponse;
import com.BagaBikes.Baga_bike_rent.configuration.JwtUtil;
import com.BagaBikes.Baga_bike_rent.model.User;
import com.BagaBikes.Baga_bike_rent.model.dto.LoginRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.SignUpRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.SignUpResponse;
import com.BagaBikes.Baga_bike_rent.repository.UserRepository;
import com.BagaBikes.Baga_bike_rent.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private  final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.SignIn(loginRequest);
    }
}
