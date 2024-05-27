package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TotemRepository {

    private static HashMap<Integer, Totem> totens;
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

}
