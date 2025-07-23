package com.casestudy.service;

import com.casestudy.model.AdminModel;
import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminrepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<AuthenticationResponse> register(AuthenticationRequest request) {
        try {
            AdminModel admin = new AdminModel();
            admin.setUsername(request.getUsername());
            admin.setPassword(request.getPassword());
            adminrepo.save(admin);

            AuthenticationResponse response = new AuthenticationResponse(
                    null, // No token on registration
                    "true",
                    admin.getId(),
                    admin.getUsername()
            );

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new AuthenticationResponse("Registration Failed", "false", null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    //    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//        } catch (Exception e) {
//            return ResponseEntity.ok(new AuthenticationResponse("no"));
//        }
//
//        UserDetails userDetails = userInfoService.loadUserByUsername(request.getUsername());
//        String jwt = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
//    }
public ResponseEntity<AuthenticationResponse> authenticateAdmin(AuthenticationRequest request) {
    try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userInfoService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        // Assuming you have an AdminModel and adminRepo
        AdminModel admin = adminrepo.findByUsername(request.getUsername());

        AuthenticationResponse response = new AuthenticationResponse(
                token,
                "true",
                admin.getId(),
                admin.getUsername()
        );

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        return new ResponseEntity<>(
                new AuthenticationResponse("Authentication failed", "false", null, null),
                HttpStatus.UNAUTHORIZED
        );
    }
}


    public String registerRawAdmin(AdminModel adminModel) {
        adminrepo.save(adminModel);
        return "User with Id: " + adminModel.getId() + " have been Registered Successfully";
    }
}