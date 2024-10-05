package com.projet.quizizback.app.quizzback.services.imp;

import com.projet.quizizback.app.quizzback.dto.QuizActivo;
import com.projet.quizizback.app.quizzback.entity.Pregunta;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class QuizActivoService {

    private final QuizService quizService;
    private final Map<String, QuizActivo> quizzesActivos = new HashMap<>();
    private static final long TIEMPO_LIMITE_PREGUNTA = 30000; // 30 segundos en milisegundos


    public QuizActivoService(QuizService quizService) {
        this.quizService = quizService;
    }

    public Pregunta iniciarQuiz(String quizId, String nombreUsuario) throws ExecutionException, InterruptedException {
        List<Pregunta> preguntas = quizService.obtenerPreguntasPorQuizId(quizId);

        // Crear una nueva lista y mezclarla
        List<Pregunta> preguntasAleatorias = new ArrayList<>(preguntas);
        Collections.shuffle(preguntasAleatorias);

        QuizActivo quizActivo = new QuizActivo();

        for (Pregunta pregunta : preguntasAleatorias) {
            pregunta.mezclarOpciones();
        }

        quizActivo.setQuizId(quizId);
        quizActivo.setNombreUsuario(nombreUsuario);
        quizActivo.setPreguntas(preguntasAleatorias);  // Usar la lista mezclada
        quizActivo.setPreguntaActual(0);
        quizActivo.setPuntuacion(0);
        quizActivo.setTiempoInicioPregunta(System.currentTimeMillis());


        String key = quizId + "_" + nombreUsuario;
        quizzesActivos.put(key, quizActivo);

        return preguntasAleatorias.get(0);  // Devolver la primera pregunta de la lista mezclada
    }

    public Pregunta siguientePregunta(String quizId, String nombreUsuario) {
        String key = quizId + "_" + nombreUsuario;
        QuizActivo quizActivo = quizzesActivos.get(key);
        if (quizActivo == null) {
            throw new IllegalStateException("No hay un quiz activo para este usuario");
        }

        quizActivo.setPreguntaActual(quizActivo.getPreguntaActual() + 1);
        if (quizActivo.getPreguntaActual() >= quizActivo.getPreguntas().size()) {
            finalizarQuiz(quizId, nombreUsuario);
            return null; // Indica que el quiz ha terminado
        }

        quizActivo.setTiempoInicioPregunta(System.currentTimeMillis());
        return quizActivo.getPreguntas().get(quizActivo.getPreguntaActual());
    }

    public boolean responderPregunta(String quizId, String nombreUsuario, int respuestaIndex) {
        String key = quizId + "_" + nombreUsuario;
        QuizActivo quizActivo = quizzesActivos.get(key);
        if (quizActivo == null) {
            throw new IllegalStateException("No hay un quiz activo para este usuario");
        }

        Pregunta preguntaActual = quizActivo.getPreguntas().get(quizActivo.getPreguntaActual());
        long tiempoTranscurrido = System.currentTimeMillis() - quizActivo.getTiempoInicioPregunta();

        if (tiempoTranscurrido <= TIEMPO_LIMITE_PREGUNTA) {
            if (respuestaIndex == preguntaActual.getRespuestaCorrecta()) {
                int puntosPregunta = (int) (TIEMPO_LIMITE_PREGUNTA - tiempoTranscurrido) / 1000;
                quizActivo.setPuntuacion(quizActivo.getPuntuacion() + puntosPregunta);
                return true;
            }
        }

        return false;
    }

    public int finalizarQuiz(String quizId, String nombreUsuario) {
        String key = quizId + "_" + nombreUsuario;
        QuizActivo quizActivo = quizzesActivos.remove(key);
        if (quizActivo == null) {
            throw new IllegalStateException("No hay un quiz activo para este usuario");
        }
        // Aquí podrías guardar el resultado en la base de datos
        // Por ejemplo: resultadoRepository.save(new Resultado(quizId, nombreUsuario, quizActivo.getPuntuacion()));
        return quizActivo.getPuntuacion();
    }
}
