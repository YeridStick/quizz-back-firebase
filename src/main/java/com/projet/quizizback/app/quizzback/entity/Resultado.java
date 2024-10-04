package com.projet.quizizback.app.quizzback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resultado {
    private String id;
    private String userId;
    private String quizId;
    private int puntuacion;
    private LocalDateTime fechaRealizacion;
}