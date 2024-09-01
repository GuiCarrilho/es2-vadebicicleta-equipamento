package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrancaRepository {

    private static final HashMap<Integer, Tranca> trancas = new HashMap<>();

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
        return new ArrayList<>(trancas.values());
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

    public boolean findTrancaByBicicleta(Integer idBicicleta){
        for (Tranca tranca : trancas.values()) {
            if (tranca.getBicicleta().equals(idBicicleta)) {
                return true;
            }
        }
        return false;
    }
}
