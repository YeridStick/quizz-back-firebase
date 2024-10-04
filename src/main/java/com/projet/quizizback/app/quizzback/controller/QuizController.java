package com.projet.quizizback.app.quizzback.controller;

import com.opencsv.exceptions.CsvException;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.services.imp.CsvService;
import com.projet.quizizback.app.quizzback.services.imp.QuizService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final CsvService csvService;

    public QuizController(QuizService quizService, CsvService csvService) {
        this.quizService = quizService;
        this.csvService = csvService;
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadQuizWithQuestions(
            @RequestParam("creadorId") String creadorId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion) {
        try {
            Quiz quiz = csvService.processCsvFile(creadorId, file, titulo, descripcion);
            return ResponseEntity.ok(quiz);
        } catch (IOException | CsvException | ExecutionException | InterruptedException e) {
            return ResponseEntity.badRequest().body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    // Otros endpoints según necesites...
}