package com.projet.quizizback.app.quizzback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pregunta {
    private String id;
    private String quizId;
    private String pregunta;
    private List<String> opciones;
    private int respuestaCorrecta;
}
