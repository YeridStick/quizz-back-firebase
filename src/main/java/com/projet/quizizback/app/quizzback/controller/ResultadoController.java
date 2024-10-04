package com.projet.quizizback.app.quizzback.controller;

import com.projet.quizizback.app.quizzback.entity.Resultado;
import com.projet.quizizback.app.quizzback.services.imp.ResultadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {
    private final ResultadoService resultadoService;

    public ResultadoController(ResultadoService resultadoService) {
        this.resultadoService = resultadoService;
    }

    @GetMapping
    public ResponseEntity<List<Resultado>> listarResultados() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(resultadoService.listarTodosLosResultados());
    }

    @PostMapping
    public ResponseEntity<Resultado> guardarResultado(@RequestBody Resultado resultado) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(resultadoService.guardarResultado(resultado));
    }

    // Otros endpoints según necesites...
}