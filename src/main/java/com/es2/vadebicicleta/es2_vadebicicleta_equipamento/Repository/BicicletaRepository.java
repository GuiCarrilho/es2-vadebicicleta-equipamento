package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Domain.Bicicleta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class BicicletaRepository {

    private static HashMap<Integer, Bicicleta> bicicletas;
    public Bicicleta save(Bicicleta bicicleta){
        bicicletas.replace(bicicleta.getId(), bicicleta);
        return bicicleta;
    }
    public List<Bicicleta> findAll(){
        List<Bicicleta> allBicicletas = new ArrayList<>(bicicletas.values());
        return allBicicletas;
    }

    public Optional<Bicicleta> findById(Integer id){
        return Optional.ofNullable(bicicletas.get(id));
    }
}
