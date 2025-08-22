package com.sbbc.mb.moviebookingapp.controller;

import com.sbbc.mb.moviebookingapp.dto.LoginResponseDTO;
import com.sbbc.mb.moviebookingapp.dto.RegisterRequestDTO;
import com.sbbc.mb.moviebookingapp.entity.User;
import com.sbbc.mb.moviebookingapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registeradminuser")
    public ResponseEntity<User> registerAdminUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authService.registerAdminlUser(registerRequestDTO));
    }



}
