package com.projet.quizizback.app.quizzback.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.projet.quizizback.app.quizzback.dto.LoginRequest;
import com.projet.quizizback.app.quizzback.dto.RefreshTokenRequest;
import com.projet.quizizback.app.quizzback.dto.RegistroRequest;
import com.projet.quizizback.app.quizzback.entity.Usuario;
import com.projet.quizizback.app.quizzback.exception.InvalidRefreshTokenException;
import com.projet.quizizback.app.quizzback.services.imp.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegistroRequest request) {
        try {
            Usuario usuario = authService.registrarUsuario(request.getEmail(), request.getPassword(), request.getNombre(), request.getRol());
            return ResponseEntity.ok(usuario);
        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Map<String, String> tokens = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(tokens);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.badRequest().body("Error de autenticación: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            Usuario usuario = authService.getUserFromToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok(usuario);
        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return ResponseEntity.badRequest().body("Token inválido");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            Map<String, String> tokens = authService.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(tokens);
        } catch (InvalidRefreshTokenException e) {
            return ResponseEntity.badRequest().body("Token de refresco inválido");
        }
    }
}