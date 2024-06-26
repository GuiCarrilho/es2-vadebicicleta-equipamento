package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TotemRepository {

    private static HashMap<Integer, Totem> totens;

    private static HashMap<Integer, List<Tranca>> trancasByTotemId;
    private IdGenerator id;

    public Totem save(Totem totem){
        if(totem.getId() == null){
            totem.setId(id.geradorId());
        }
        totens.replace(totem.getId(), totem);
        return totem;
    }

    public List<Totem> findAll(){
        return (List<Totem>) totens.values();
    }

    public Optional<Totem> findById(Integer id){
        return Optional.ofNullable(totens.get(id));
    }

    public Totem deleteById(Integer idTotem){
        if(findById(idTotem).isPresent()){
            return totens.remove(idTotem);
        }
        return null;
    }

    public void addTrancasByTotemId(Integer idTotem, List<Tranca> trancas){
        trancasByTotemId.put(idTotem, trancas);
    }

    public List<Tranca> findTrancasByTotemId(Integer idTotem){
        List<Tranca> trancas = trancasByTotemId.get(idTotem);

        return trancas;
    }

}
