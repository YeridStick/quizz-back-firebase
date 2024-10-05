package com.projet.quizizback.app.quizzback.repository;

import com.google.cloud.firestore.Firestore;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Repository
public class QuizRepository {
    private final Firestore firestore;

    public QuizRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Quiz save(Quiz quiz) throws ExecutionException, InterruptedException {
        if (quiz.getId() == null) {
            quiz.setId(firestore.collection("quizzes").document().getId());
        }
        firestore.collection("quizzes").document(quiz.getId()).set(quiz).get();
        return quiz;
    }

    public Quiz findById(String id) throws ExecutionException, InterruptedException {
        return firestore.collection("quizzes").document(id).get().get().toObject(Quiz.class);
    }

    public List<Quiz> findAll() throws ExecutionException, InterruptedException {
        return firestore.collection("quizzes").get().get().getDocuments().stream()
                .map(doc -> doc.toObject(Quiz.class))
                .collect(Collectors.toList());
    }


}