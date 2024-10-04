package com.projet.quizizback.app.quizzback.controller;

import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.services.imp.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> listarQuizzes() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(quizService.listarTodosLosQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> obtenerQuiz(@PathVariable String id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(quizService.obtenerQuizPorId(id));
    }

    @PostMapping
    public ResponseEntity<Quiz> crearQuiz(@RequestBody Quiz quiz) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(quizService.crearQuiz(quiz));
    }

    // Otros endpoints según necesites...
}