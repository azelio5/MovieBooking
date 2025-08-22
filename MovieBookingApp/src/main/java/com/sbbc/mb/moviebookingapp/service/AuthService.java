package com.sbbc.mb.moviebookingapp.service;

import com.sbbc.mb.moviebookingapp.dto.LoginRequestDTO;
import com.sbbc.mb.moviebookingapp.dto.LoginResponseDTO;
import com.sbbc.mb.moviebookingapp.dto.RegisterRequestDTO;
import com.sbbc.mb.moviebookingapp.entity.User;
import com.sbbc.mb.moviebookingapp.exception.NotFoundException;
import com.sbbc.mb.moviebookingapp.jwt.JwtService;
import com.sbbc.mb.moviebookingapp.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findUserByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already registered");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .email(registerRequestDTO.getEmail())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }


    public User registerAdminlUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findUserByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already registered");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .email(registerRequestDTO.getEmail())
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findUserByUsername(loginRequestDTO.getUsername()).orElseThrow(() ->
                new NotFoundException("User not found with username: " + loginRequestDTO.getUsername()));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

        String token = jwtService.generateToken(user);

        return LoginResponseDTO.
                builder().
                jwtToken(token).
                username(user.getUsername()).
                roles(user.getRoles()).
                build();
    }
}
