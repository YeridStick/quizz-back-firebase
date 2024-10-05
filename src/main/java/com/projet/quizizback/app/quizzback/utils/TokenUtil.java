package com.projet.quizizback.app.quizzback.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenUtil {

    private final FirebaseAuth firebaseAuth;

    public TokenUtil(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public String extractUserIdFromToken(String token) throws FirebaseAuthException {
        log.info("Intentando extraer ID de usuario del token");
        try {
            String idToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            log.debug("Token a verificar: {}", idToken);

            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            log.info("ID de usuario extraído exitosamente: {}", uid);

            return uid;
        } catch (FirebaseAuthException e) {
            log.error("Error al verificar el token: ", e);
            throw e;
        }
    }
}