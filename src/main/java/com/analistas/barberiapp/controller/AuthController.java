package com.analistas.barberiapp.controller;

import com.analistas.barberiapp.dto.LoginRequest;
import com.analistas.barberiapp.dto.LoginResponse;
import com.analistas.barberiapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/setup")
    public ResponseEntity<String> setup() {
        authService.registrar("Dueño", "admin@barberia.com", "admin123");
        return ResponseEntity.ok("Usuario creado correctamente");
    }

    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        return ResponseEntity
                .ok(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("admin123"));
    }
}