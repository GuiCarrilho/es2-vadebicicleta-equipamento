package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class BicicletaRepository {

    private static HashMap<Integer, Bicicleta> bicicletas = new HashMap<>();
    private final IdGenerator id;

    public BicicletaRepository(IdGenerator id){
        this.id = id;
    }

    public Bicicleta save(Bicicleta bicicleta){
        if (findById(bicicleta.getId()).isPresent()) {
            bicicletas.replace(bicicleta.getId(), bicicleta);
            return bicicleta;
        }
        Integer idBicicleta = id.idBicicletaGenerator();
        bicicleta.setId(idBicicleta);
        bicicletas.put(idBicicleta, bicicleta);
        return bicicleta;
    }
    public List<Bicicleta> findAll(){
        return (List<Bicicleta>) bicicletas.values();
    }

    public Optional<Bicicleta> findById(Integer id){
        return Optional.ofNullable(bicicletas.get(id));
    }

    public boolean deleteById(Integer idBicicleta){
        if(findById(idBicicleta).isPresent()){
            bicicletas.remove(idBicicleta);
            return true;
        }
        return false;
    }
}
