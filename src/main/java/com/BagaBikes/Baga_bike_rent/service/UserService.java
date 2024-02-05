package com.BagaBikes.Baga_bike_rent.service;

import com.BagaBikes.Baga_bike_rent.configuration.JwtResponse;
import com.BagaBikes.Baga_bike_rent.configuration.JwtUtil;
import com.BagaBikes.Baga_bike_rent.model.Role;
import com.BagaBikes.Baga_bike_rent.model.User;
import com.BagaBikes.Baga_bike_rent.model.dto.LoginRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.SignUpRequest;
import com.BagaBikes.Baga_bike_rent.model.dto.SignUpResponse;
import com.BagaBikes.Baga_bike_rent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtTokenUtil;

     @Transactional
    public ResponseEntity<SignUpResponse> signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new SignUpResponse("Error: Username is already taken!"));
        }
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setRoles(signUpRequest.getRoles());
        user.setPassword(signUpRequest.getPassword()); // This will encode the password

        userRepository.save(user);
        return ResponseEntity.ok(new SignUpResponse("User registered successfully!"));


    }
     @Transactional
    public ResponseEntity<?> SignIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new JwtResponse(jwt));
        }


}
