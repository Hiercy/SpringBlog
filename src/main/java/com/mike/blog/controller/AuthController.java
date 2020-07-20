package com.mike.blog.controller;

import com.mike.blog.dto.AuthResponse;
import com.mike.blog.dto.LoginDto;
import com.mike.blog.dto.RegisterDto;
import com.mike.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
}
