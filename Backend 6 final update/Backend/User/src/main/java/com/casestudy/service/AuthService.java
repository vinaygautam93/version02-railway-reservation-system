package com.casestudy.service;

import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ User Registration
    public ResponseEntity<AuthenticationResponse> registerUser(AuthenticationRequest authRequest) {
        // Null check for safety
        if (authRequest.getUsername() == null || authRequest.getPassword() == null) {
            return new ResponseEntity<>(
                    new AuthenticationResponse("Missing username or password", "0", null, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Check if user already exists in DB
        UserModel existingUser = userRepo.findByUsername(authRequest.getUsername());

        if (existingUser != null) {
            return new ResponseEntity<>(
                    new AuthenticationResponse("Registration failed: User already exists", "0", null, null),
                    HttpStatus.OK
            );
        }

        // Save new user
        UserModel userModel = new UserModel();
        userModel.setUsername(authRequest.getUsername());
        userModel.setPassword(authRequest.getPassword());

        userRepo.save(userModel);

        return new ResponseEntity<>(
                new AuthenticationResponse(
                        authRequest.getUsername() + " registered successfully",
                        "1",
                        userModel.getId(),
                        userModel.getUsername()
                ),
                HttpStatus.OK
        );
    }


    // ✅ User Login (Authenticate)
    public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = userInfoService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails);

            UserModel user = userRepo.findByUsername(request.getUsername());

            AuthenticationResponse response = new AuthenticationResponse(
                    token,
                    "true",
                    user.getId(),
                    user.getUsername()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthenticationResponse("Authentication failed", "false", null, null),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }

    // ✅ Load user by username
    public UserDetails loadUserByUsername(String username) {
        return userInfoService.loadUserByUsername(username);
    }

    // ✅ Get all users
    public List<UserModel> getAllUsers() {
        return userRepo.findAll();
    }
}
