package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotemService {

    private final TotemRepository repository;

    @Autowired
    public TotemService(TotemRepository repository) {
        this.repository = repository;
    }

    public List<Totem> getAll() {
        return repository.findAll();
    }

    public Totem save(Totem totem) {
        return repository.save(totem);
    }
}
