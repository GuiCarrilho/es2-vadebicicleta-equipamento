package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class BicicletaRepository {

    private static HashMap<Integer, Bicicleta> bicicletas;
    private IdGenerator id;

    public Bicicleta save(Bicicleta bicicleta){
        if (bicicleta.getId() == null) {
            bicicleta.setId(id.geradorId());
        }
        bicicletas.replace(bicicleta.getId(), bicicleta);
        return bicicleta;
    }
    public List<Bicicleta> findAll(){
        return (List<Bicicleta>) bicicletas.values();
    }

    public Optional<Bicicleta> findById(Integer id){
        return Optional.ofNullable(bicicletas.get(id));
    }

    public Bicicleta deleteById(Integer idBicicleta){
        if(findById(idBicicleta).isEmpty()){
            return bicicletas.remove(idBicicleta);
        }
        return null;
    }
}
