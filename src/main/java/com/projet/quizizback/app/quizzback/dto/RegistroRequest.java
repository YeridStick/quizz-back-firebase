package com.projet.quizizback.app.quizzback.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String email;
    private String password;
    private String nombre;
    private String rol;
}