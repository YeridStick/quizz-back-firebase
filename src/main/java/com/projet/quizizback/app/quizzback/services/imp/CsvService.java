package com.projet.quizizback.app.quizzback.services.imp;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import com.projet.quizizback.app.quizzback.entity.Usuario;
import com.projet.quizizback.app.quizzback.exception.InvalidRefreshTokenException;
import com.projet.quizizback.app.quizzback.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CsvService {

    private final PreguntaService preguntaService;
    private final QuizService quizService;
    private final UsuarioRepository usuarioRepository;

    public CsvService(PreguntaService preguntaService, QuizService quizService, UsuarioRepository usuarioRepository) {
        this.preguntaService = preguntaService;
        this.quizService = quizService;
        this.usuarioRepository = usuarioRepository;
    }

    public Quiz processCsvFile(String creadorId, byte[] fileContent, String fileName, String titulo, String descripcion) throws ExecutionException, InterruptedException, IOException, CsvException {

        Usuario usuario = usuarioRepository.findById(creadorId);

        if (!"teacher".equals(usuario.getRol())) {
            throw new InvalidRefreshTokenException("No tiene permisos para crear un quiz. Solo los profesores pueden hacerlo.");
        }

        // Crear el quiz
        Quiz quiz = new Quiz();
        quiz.setCreadorId(creadorId);
        quiz.setTitulo(titulo);
        quiz.setDescripcion(descripcion);
        quiz = quizService.crearQuiz(quiz);

        // Procesar el contenido CSV para agregar las preguntas
        List<Pregunta> preguntas = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(fileContent)))) {
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                if (row.length < 5) { // Asegurarse de que haya al menos una pregunta y 4 opciones
                    continue;
                }

                String preguntaText = row[0];
                List<String> opciones = new ArrayList<>(Arrays.asList(row).subList(1, 5));

                int respuestaCorrecta = 0; // Aquí puedes ajustar la lógica para determinar la respuesta correcta

                Pregunta pregunta = new Pregunta();
                pregunta.setQuizId(quiz.getId());
                pregunta.setPregunta(preguntaText);
                pregunta.setOpciones(opciones);
                pregunta.setRespuestaCorrecta(respuestaCorrecta);

                preguntas.add(pregunta);
            }
        }

        // Guardar las preguntas
        for (Pregunta pregunta : preguntas) {
            preguntaService.crearPregunta(pregunta);
        }

        return quiz;
    }
}