package com.projet.quizizback.app.quizzback.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private String id;
    private String email;
    private String nombre;
    private String rol;
}
