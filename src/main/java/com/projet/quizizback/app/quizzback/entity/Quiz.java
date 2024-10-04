package com.projet.quizizback.app.quizzback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    private String id;
    private String titulo;
    private String descripcion;
    private String creadorId;
}
