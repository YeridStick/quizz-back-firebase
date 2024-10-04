package com.projet.quizizback.app.quizzback.services.imp;

import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz crearQuiz(Quiz quiz) throws ExecutionException, InterruptedException {
        return quizRepository.save(quiz);
    }

    public Quiz obtenerQuizPorId(String id) throws ExecutionException, InterruptedException {
        return quizRepository.findById(id);
    }

    public List<Quiz> listarTodosLosQuizzes() throws ExecutionException, InterruptedException {
        return quizRepository.findAll();
    }

    // Otros métodos según necesites
}