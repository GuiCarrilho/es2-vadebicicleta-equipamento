package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Domain.Bicicleta;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BicicletaRepository {
    Bicicleta save(Bicicleta bicicleta);
    List<Bicicleta> findAll();
}
