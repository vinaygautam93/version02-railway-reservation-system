package com.casestudy.controller;

import com.casestudy.model.AdminModel;
import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AuthController {

	@Autowired
	private AuthService authService;

	Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/register")
	private ResponseEntity<AuthenticationResponse> registerClientToken(@RequestBody AuthenticationRequest request) {
		logger.info("[register] info message");
		System.out.println("front end re");
		return authService.register(request);
	}

	@PostMapping("/authenticate")
	private ResponseEntity<AuthenticationResponse> authenticateClientToken(@RequestBody AuthenticationRequest request) {
		logger.info("[authenticate] info message");
		System.out.println("front end re");
		return authService.authenticateAdmin(request);
	}

	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}

	@PostMapping("/reg")
	public String adduser(@RequestBody AdminModel adminModel) {
		return authService.registerRawAdmin(adminModel);
	}
}