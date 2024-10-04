package com.projet.quizizback.app.quizzback.services.imp;

import com.projet.quizizback.app.quizzback.entity.Pregunta;
import com.projet.quizizback.app.quizzback.repository.PreguntaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PreguntaService {
    private final PreguntaRepository preguntaRepository;

    public PreguntaService(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    public Pregunta crearPregunta(Pregunta pregunta) throws ExecutionException, InterruptedException {
        return preguntaRepository.save(pregunta);
    }

    public List<Pregunta> obtenerPreguntasPorQuizId(String quizId) throws ExecutionException, InterruptedException {
        return preguntaRepository.findByQuizId(quizId);
    }

    public List<Pregunta> listarTodasLasPreguntas() throws ExecutionException, InterruptedException {
        return preguntaRepository.findAll();
    }

    // Otros métodos según necesites
}