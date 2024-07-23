package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrancaRepository {

    private static HashMap<Integer, Tranca> trancas = new HashMap<>();

    private final IdGenerator id;

    public TrancaRepository(IdGenerator id){
        this.id = id;
    }

    public Tranca save(Tranca tranca){
        if(findById(tranca.getId()).isPresent()){
            trancas.replace(tranca.getId(), tranca);
            return tranca;
        }
        Integer idTranca = id.idTrancaGenerator();
        tranca.setId(idTranca);
        trancas.put(idTranca, tranca);
        return tranca;
    }

    public List<Tranca> findAll(){
        List<Tranca> allTrancas = new ArrayList<>();
        allTrancas.addAll(trancas.values());
        return allTrancas;
    }

    public Optional<Tranca> findById(Integer id){
        return Optional.ofNullable(trancas.get(id));
    }

    public boolean deleteById(Integer idTranca){
        if(findById(idTranca).isPresent()){
            trancas.remove(idTranca);
            return true;
        }
        return false;
    }
}
