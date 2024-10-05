package com.projet.quizizback.app.quizzback.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Resultado {
    private String id;
    private String quizId;
    private String nombreUsuario;
    private int puntuacion;
    private Date fecha;

    public Resultado(String quizId, String nombreUsuario, int puntuacion) {
        this.quizId = quizId;
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
        this.fecha = new Date();
    }
}