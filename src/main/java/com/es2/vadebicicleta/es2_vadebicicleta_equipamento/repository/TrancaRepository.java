package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TrancaRepository {

    private static HashMap<Integer, Tranca> trancas;

    private IdGenerator id;

    public Tranca save(Tranca tranca){
        if(tranca.getId() == null){
            tranca.setId(id.geradorId());
        }
        trancas.replace(tranca.getId(), tranca);
        return tranca;
    }

    public List<Tranca> findAll(){
        return (List<Tranca>) trancas.values();
    }

    public Optional<Tranca> findById(Integer id){
        return Optional.ofNullable(trancas.get(id));
    }

}
