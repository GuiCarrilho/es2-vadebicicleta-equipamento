package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrancaService {

    private final TrancaRepository repository;

    @Autowired
    public TrancaService(TrancaRepository repository) {
        this.repository = repository;
    }

    public Tranca save(Tranca tranca) {

        return repository.save(tranca);
    }


    public List<Tranca> getAll() {
        return repository.findAll();
    }

}
