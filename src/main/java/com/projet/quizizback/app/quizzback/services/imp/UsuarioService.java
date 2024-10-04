package com.projet.quizizback.app.quizzback.services.imp;

import com.projet.quizizback.app.quizzback.entity.Usuario;
import com.projet.quizizback.app.quizzback.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(String id) throws ExecutionException, InterruptedException {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> listarTodosLosUsuarios() throws ExecutionException, InterruptedException {
        return usuarioRepository.findAll();
    }

}