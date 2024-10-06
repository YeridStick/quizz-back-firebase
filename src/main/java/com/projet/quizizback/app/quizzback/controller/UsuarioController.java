package com.projet.quizizback.app.quizzback.controller;

import com.projet.quizizback.app.quizzback.entity.Usuario;
import com.projet.quizizback.app.quizzback.services.imp.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    // Otros endpoints según necesites...
}