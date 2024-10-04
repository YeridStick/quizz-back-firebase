package com.projet.quizizback.app.quizzback.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.projet.quizizback.app.quizzback.entity.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class UsuarioRepository {
    private final Firestore firestore;

    public UsuarioRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Usuario save(Usuario usuario) throws ExecutionException, InterruptedException {
        if (usuario.getId() == null) {
            usuario.setId(firestore.collection("usuarios").document().getId());
        }
        firestore.collection("usuarios").document(usuario.getId()).set(usuario).get();
        return usuario;
    }

    public Usuario findById(String id) throws ExecutionException, InterruptedException {
        return firestore.collection("usuarios").document(id).get().get().toObject(Usuario.class);
    }

    public List<Usuario> findAll() throws ExecutionException, InterruptedException {
        log.info("Fetching all users from Firestore...");
        ApiFuture<QuerySnapshot> future = firestore.collection("usuarios").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        log.info("Found {} users", documents.size());
        return documents.stream()
                .map(doc -> doc.toObject(Usuario.class))
                .collect(Collectors.toList());
    }

    // Otros métodos según necesites
}