package com.projet.quizizback.app.quizzback.dto;

import com.projet.quizizback.app.quizzback.entity.Pregunta;
import lombok.Data;

import java.util.List;

@Data
public class QuizActivo {
    private String quizId;
    private String nombreUsuario;
    private List<Pregunta> preguntas;
    private int preguntaActual;
    private int puntuacion;
    private long tiempoInicioPregunta;
}