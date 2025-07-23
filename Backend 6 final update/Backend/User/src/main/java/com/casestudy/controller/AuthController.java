package com.casestudy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.model.UserModel;
import com.casestudy.service.AuthService;
import com.casestudy.service.JwtUtil;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")

@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerClientToken(@RequestBody AuthenticationRequest authRequest) {
        logger.info("[register] Registering user {}", authRequest.getUsername());
        return authService.registerUser(authRequest);
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticateClientToken(@RequestBody AuthenticationRequest authRequest) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        } catch (Exception e) {
//            return ResponseEntity.ok(new AuthenticationResponse("Authentication failed", "false"));
//        }
//        UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
//        String jwt = jwtUtil.generateToken(userDetails);
//        logger.info("[authenticate] User {} authenticated successfully", authRequest.getUsername());
//        return ResponseEntity.ok(new AuthenticationResponse(jwt, "true"));
//    }
@PostMapping("/authenticate")
public ResponseEntity<?> authenticateClientToken(@RequestBody AuthenticationRequest authRequest) {
    return authService.authenticateUser(authRequest);
}


    @GetMapping("/viewprofile")
    public List<UserModel> getUserProfiles() {
        logger.info("[viewprofile] Fetching all user profiles");
        return authService.getAllUsers();
    }

    @GetMapping("/hello")
    public String firstPage() {
        return "Hello World";
    }
}