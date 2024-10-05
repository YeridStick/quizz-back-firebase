package com.projet.quizizback.app.quizzback.repository;

import com.google.cloud.firestore.Firestore;
import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class PreguntaRepository {
    private final Firestore firestore;

    public PreguntaRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Pregunta save(Pregunta pregunta) throws ExecutionException, InterruptedException {
        if (pregunta.getId() == null) {
            pregunta.setId(firestore.collection("preguntas").document().getId());
        }
        firestore.collection("preguntas").document(pregunta.getId()).set(pregunta).get();
        return pregunta;
    }

    public List<Pregunta> findByQuizId(String quizId) throws ExecutionException, InterruptedException {
        return firestore.collection("preguntas")
                .whereEqualTo("quizId", quizId)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(doc -> doc.toObject(Pregunta.class))
                .collect(Collectors.toList());
    }

    public List<Pregunta> findAll() throws ExecutionException, InterruptedException {
        return firestore.collection("preguntas").get().get().getDocuments().stream()
                .map(doc -> doc.toObject(Pregunta.class))
                .collect(Collectors.toList());
    }

    public List<Pregunta> obtenerPreguntasPorQuizId(String quizId) throws ExecutionException, InterruptedException {
        return firestore.collection("preguntas")
                .whereEqualTo("quizId", quizId)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(doc -> doc.toObject(Pregunta.class))
                .collect(Collectors.toList());
    }
}