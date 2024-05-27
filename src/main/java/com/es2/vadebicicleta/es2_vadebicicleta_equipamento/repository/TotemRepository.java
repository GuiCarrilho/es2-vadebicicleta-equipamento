package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

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

}
