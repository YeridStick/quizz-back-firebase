package com.projet.quizizback.app.quizzback.services.imp;

import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.repository.PreguntaRepository;
import com.projet.quizizback.app.quizzback.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final PreguntaRepository preguntaRepository;

    public QuizService(QuizRepository quizRepository, PreguntaRepository preguntaRepository) {
        this.quizRepository = quizRepository;
        this.preguntaRepository = preguntaRepository;
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

    public List<Pregunta> obtenerPreguntasPorQuizId(String quizId) throws ExecutionException, InterruptedException {
        return preguntaRepository.obtenerPreguntasPorQuizId(quizId);
    }
}