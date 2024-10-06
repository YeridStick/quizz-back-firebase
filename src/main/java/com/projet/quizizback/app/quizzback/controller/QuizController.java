package com.projet.quizizback.app.quizzback.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.opencsv.exceptions.CsvException;
import com.projet.quizizback.app.quizzback.dto.QuizUploadRequest;
import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.services.imp.CsvService;
import com.projet.quizizback.app.quizzback.services.imp.QuizActivoService;
import com.projet.quizizback.app.quizzback.services.imp.QuizService;
import com.projet.quizizback.app.quizzback.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequestMapping("/api/quizzes")
@CrossOrigin("*")
public class QuizController {
    private final QuizService quizService;
    private final CsvService csvService;
    private final QuizActivoService quizActivoService;
    private final TokenUtil tokenUtil;



    public QuizController(QuizService quizService, CsvService csvService, QuizActivoService quizActivoService, TokenUtil tokenUtil) {
        this.quizService = quizService;
        this.csvService = csvService;
        this.quizActivoService = quizActivoService;
        this.tokenUtil = tokenUtil;
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

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadQuizWithQuestions(@RequestBody QuizUploadRequest request) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(request.getFileContent());
            Quiz quiz = csvService.processCsvFile(request.getCreadorId(), decodedBytes, request.getFileName(), request.getTitulo(), request.getDescripcion());
            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    @PostMapping("/iniciar/{quizId}")
    public ResponseEntity<Pregunta> iniciarQuiz(@PathVariable String quizId,
                                                @RequestParam String nombreUsuario) {
        try {
            log.info("Iniciando quiz {} para usuario {}", quizId, nombreUsuario);
            Pregunta primeraPregunta = quizActivoService.iniciarQuiz(quizId, nombreUsuario);
            log.info("Quiz iniciado exitosamente para el usuario: {}", nombreUsuario);
            return ResponseEntity.ok(primeraPregunta);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error al iniciar el quiz: ", e);
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("/{quizId}/responder")
    public ResponseEntity<?> responderPregunta(@PathVariable String quizId,
                                               @RequestParam String nombreUsuario,
                                               @RequestParam int respuestaIndex) {
        boolean esCorrecta = quizActivoService.responderPregunta(quizId, nombreUsuario, respuestaIndex);
        Pregunta siguientePregunta = quizActivoService.siguientePregunta(quizId, nombreUsuario);
        if (siguientePregunta == null) {
            int puntuacionFinal = quizActivoService.finalizarQuiz(quizId, nombreUsuario);
            return ResponseEntity.ok(Map.of("mensaje", "Quiz finalizado", "puntuacion", puntuacionFinal));
        }
        return ResponseEntity.ok(Map.of("esCorrecta", esCorrecta, "siguientePregunta", siguientePregunta));
    }

    // Otros endpoints según necesites...
}