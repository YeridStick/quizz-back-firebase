package com.projet.quizizback.app.quizzback.controller;

import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.services.imp.PreguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {
    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    @GetMapping
    public ResponseEntity<List<Pregunta>> listarPreguntas() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(preguntaService.listarTodasLasPreguntas());
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Pregunta>> listarPreguntasPorQuiz(@PathVariable String quizId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(preguntaService.obtenerPreguntasPorQuizId(quizId));
    }

    @PostMapping
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(preguntaService.crearPregunta(pregunta));
    }

    // Otros endpoints según necesites...
}