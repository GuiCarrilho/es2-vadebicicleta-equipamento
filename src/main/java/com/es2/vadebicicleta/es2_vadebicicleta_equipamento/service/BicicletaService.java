package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BicicletaService {

    private IdGenerator id;

    private final BicicletaRepository repository;

    @Autowired
    public BicicletaService(BicicletaRepository repository) {
        this.repository = repository;
    }


    public Bicicleta save(Bicicleta bicicleta) {
        if (bicicleta.getId() == null) {
            bicicleta.setId(id.geradorId());
        }
        return repository.save(bicicleta);
    }

    public List<Bicicleta> listarBicicletas() {
        return repository.findAll();
    }

    public Optional<Bicicleta> getById(Integer id){
        return repository.findById(id);
    }
}

