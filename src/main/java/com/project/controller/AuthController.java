package com.project.controller;

import com.project.persistence.dto.request.LoginRequest;
import com.project.persistence.dto.request.RefreshRequest;
import com.project.persistence.dto.request.RegistrationRequest;
import com.project.persistence.dto.response.AuthResponse;
import com.project.persistence.dto.response.AuthTokenRefreshResponse;
import com.project.persistence.dto.response.MessageResponse;
import com.project.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(authService.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authService.loginUser(request), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthTokenRefreshResponse> refreshAccessToken(@RequestBody RefreshRequest request) {
        return new ResponseEntity<>(authService.refreshAccessToken(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader("authorization") String token,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
        return new ResponseEntity<>(authService.logoutUser(token, request, response), HttpStatus.OK);
    }

}
