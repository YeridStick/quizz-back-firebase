package com.projet.quizizback.app.quizzback.services.imp;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.entity.Quiz;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public CsvService(PreguntaService preguntaService, QuizService quizService) {
        this.preguntaService = preguntaService;
        this.quizService = quizService;
    }

    public Quiz processCsvFile(String creadorId, MultipartFile file, String titulo, String descripcion) throws ExecutionException, InterruptedException, IOException, CsvException {
        Quiz quiz = new Quiz();
        quiz.setCreadorId(creadorId);
        quiz.setTitulo(titulo);
        quiz.setDescripcion(descripcion);
        quiz = quizService.crearQuiz(quiz);

        List<Pregunta> preguntas = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                if (row.length < 5) { // Asegurarse de que haya al menos una pregunta y 4 opciones
                    continue;
                }

                String preguntaText = row[0];
                List<String> opciones = new ArrayList<>(Arrays.asList(row).subList(1, 5));

                int respuestaCorrecta = 0;

                Pregunta pregunta = new Pregunta();
                pregunta.setQuizId(quiz.getId());
                pregunta.setPregunta(preguntaText);
                pregunta.setOpciones(opciones);
                pregunta.setRespuestaCorrecta(respuestaCorrecta);

                preguntas.add(pregunta);
            }
        }

        for (Pregunta pregunta : preguntas) {
            preguntaService.crearPregunta(pregunta);
        }

        return quiz;
    }
}