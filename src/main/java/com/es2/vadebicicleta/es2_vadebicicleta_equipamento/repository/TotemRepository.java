package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import org.springframework.stereotype.Repository;

import java.util.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TotemRepository {

    private static final HashMap<Integer, Totem> totens = new HashMap<>();

    private final IdGenerator id;

    public TotemRepository(IdGenerator id){
        this.id = id;
    }

   public Totem save(Totem totem){
        if (findById(totem.getId()).isPresent()) {
            totens.replace(totem.getId(), totem);
            return totem;
        }
        Integer idTotem = id.idTotemGenerator();
        totem.setId(idTotem);
        totens.put(idTotem, totem);
        return totem;
    }

    public List<Totem> findAll(){
        List<Totem> allTotens = new ArrayList<>();
        allTotens.addAll(totens.values());
        return allTotens;
    }

    public Optional<Totem> findById(Integer id){
        return Optional.ofNullable(totens.get(id));
    }

    public boolean deleteById(Integer idTotem){
        if(findById(idTotem).isPresent()){
            totens.remove(idTotem);
            return true;
        }
        return false;
    }
}
