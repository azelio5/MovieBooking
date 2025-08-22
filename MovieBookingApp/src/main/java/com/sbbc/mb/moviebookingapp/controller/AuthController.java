package com.sbbc.mb.moviebookingapp.controller;

import com.sbbc.mb.moviebookingapp.dto.LoginRequestDTO;
import com.sbbc.mb.moviebookingapp.dto.LoginResponseDTO;
import com.sbbc.mb.moviebookingapp.dto.RegisterRequestDTO;
import com.sbbc.mb.moviebookingapp.entity.User;

import com.sbbc.mb.moviebookingapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registernormaluser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authService.registerNormalUser(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

}
