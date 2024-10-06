package com.projet.quizizback.app.quizzback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizUploadRequest {
    private String creadorId;
    private String fileContent;
    private String fileName;
    private String titulo;
    private String descripcion;
}
