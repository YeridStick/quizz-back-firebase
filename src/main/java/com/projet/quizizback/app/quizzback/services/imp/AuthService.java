package com.projet.quizizback.app.quizzback.services.imp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.entity.Usuario;
import com.projet.quizizback.app.quizzback.exception.InvalidRefreshTokenException;
import com.projet.quizizback.app.quizzback.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class AuthService {
    private final FirebaseAuth firebaseAuth;
    private final UsuarioRepository usuarioRepository;
    private final Map<String, String> refreshTokens = new HashMap<>();

    public AuthService(FirebaseAuth firebaseAuth, UsuarioRepository usuarioRepository) {
        this.firebaseAuth = firebaseAuth;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(String email, String password, String nombre, String rol) throws FirebaseAuthException, ExecutionException, InterruptedException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(nombre);
        UserRecord userRecord = firebaseAuth.createUser(request);

        Usuario usuario = new Usuario();
        usuario.setId(userRecord.getUid());
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    public Map<String, Object> login(String email, String password) throws FirebaseAuthException {
        try {
            // Obtener el usuario por email
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);

            // Aquí deberías verificar la contraseña
            // NOTA: Implementa una verificación de contraseña segura aquí

            // Buscar el usuario en tu base de datos
            Usuario usuario = usuarioRepository.findByEmail(email);
            if (usuario == null) {
                throw new InvalidRefreshTokenException("Usurio no encontrado");
            }

            // Crear token personalizado
            String customToken = firebaseAuth.createCustomToken(userRecord.getUid());

            // Crear refresh token
            String refreshToken = UUID.randomUUID().toString();
            refreshTokens.put(refreshToken, userRecord.getUid());

            Map<String, Object> response = new HashMap<>();
            response.put("token", customToken);
            response.put("refreshToken", refreshToken);
            response.put("userId", usuario.getId());
            response.put("nombre", usuario.getNombre());
            response.put("rol", usuario.getRol());

            return response;
        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            throw new InvalidRefreshTokenException("Autenticación fallida" + e.getMessage());
        }
    }

    public Map<String, String> refreshToken(String refreshToken) {
        String uid = refreshTokens.get(refreshToken);
        if (uid == null) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        try {
            String newCustomToken = firebaseAuth.createCustomToken(uid);
            String newRefreshToken = UUID.randomUUID().toString();

            // Eliminar el antiguo refresh token y añadir el nuevo
            refreshTokens.remove(refreshToken);
            refreshTokens.put(newRefreshToken, uid);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", newCustomToken);
            tokens.put("refreshToken", newRefreshToken);
            return tokens;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error creating custom token", e);
        }
    }

    public Usuario getUserFromToken(String token) throws FirebaseAuthException, ExecutionException, InterruptedException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);
        String uid = decodedToken.getUid();
        return usuarioRepository.findById(uid);
    }


    public void ejmplo(Quiz quizelement) {
        Quiz quiz = new Quiz();
        if (quizelement.getCreadorId() != null) {
            
        }
    }
}