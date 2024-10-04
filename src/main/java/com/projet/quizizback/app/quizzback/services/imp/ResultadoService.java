package com.projet.quizizback.app.quizzback.services.imp;

import com.projet.quizizback.app.quizzback.entity.Resultado;
import com.projet.quizizback.app.quizzback.repository.ResultadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ResultadoService {
    private final ResultadoRepository resultadoRepository;

    public ResultadoService(ResultadoRepository resultadoRepository) {
        this.resultadoRepository = resultadoRepository;
    }

    public Resultado guardarResultado(Resultado resultado) throws ExecutionException, InterruptedException {
        return resultadoRepository.save(resultado);
    }

    public List<Resultado> listarTodosLosResultados() throws ExecutionException, InterruptedException {
        return resultadoRepository.findAll();
    }

    // Otros métodos según necesites
}
