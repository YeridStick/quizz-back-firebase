package com.projet.quizizback.app.quizzback.repository;


import com.google.cloud.firestore.Firestore;
import com.projet.quizizback.app.quizzback.entity.Resultado;
import com.projet.quizizback.app.quizzback.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class ResultadoRepository {
    private final Firestore firestore;

    public ResultadoRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Resultado save(Resultado resultado) throws ExecutionException, InterruptedException {
        if (resultado.getId() == null) {
            resultado.setId(firestore.collection("resultados").document().getId());
        }
        firestore.collection("resultados").document(resultado.getId()).set(resultado).get();
        return resultado;
    }

    public List<Resultado> findAll() throws ExecutionException, InterruptedException {
        return firestore.collection("resultados").get().get().getDocuments().stream()
                .map(doc -> doc.toObject(Resultado.class))
                .collect(Collectors.toList());
    }

}