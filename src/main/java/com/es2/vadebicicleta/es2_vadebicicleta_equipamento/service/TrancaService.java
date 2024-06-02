package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<Tranca> getById(Integer id){
        return repository.findById(id);
    }


}
