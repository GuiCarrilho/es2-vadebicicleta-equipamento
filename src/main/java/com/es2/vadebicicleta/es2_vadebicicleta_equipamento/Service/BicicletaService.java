package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Repository.BicicletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicicletaService {
    private static int contador;

    @Autowired
    private BicicletaRepository repository;


    public Bicicleta cadastrarBicicleta(Bicicleta bicicleta) {
        if (bicicleta.getId() == null) {
            bicicleta.setId(contador++);
        }
        return repository.save(bicicleta);
    }

    public List<Bicicleta> listarBicicletas() {
        return repository.findAll();
    }
}

