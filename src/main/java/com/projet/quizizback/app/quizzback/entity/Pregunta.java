package com.projet.quizizback.app.quizzback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Pregunta {
    private String id;
    private String quizId;
    private String pregunta;
    private List<String> opciones;
    private int respuestaCorrecta;

    public void mezclarOpciones() {
        List<String> opcionesMezcladas = new ArrayList<>(opciones);
        int respuestaCorrectaOriginal = respuestaCorrecta;
        String opcionCorrecta = opciones.get(respuestaCorrecta);

        Collections.shuffle(opcionesMezcladas);

        opciones = opcionesMezcladas;
        respuestaCorrecta = opcionesMezcladas.indexOf(opcionCorrecta);
    }
}