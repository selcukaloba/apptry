package com.selcukaloba.apptry.controller;

import com.selcukaloba.apptry.dto.auth.LoginRequest;
import com.selcukaloba.apptry.dto.auth.RefreshTokenRequest;
import com.selcukaloba.apptry.dto.auth.RegisterRequest;
import com.selcukaloba.apptry.dto.auth.AuthResponse;
import com.selcukaloba.apptry.repository.RefreshTokenRepository;
import com.selcukaloba.apptry.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest)
    {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public void logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        authService.logout(refreshTokenRequest);
    }
}
