package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TotemRepository {

    private static final HashMap<Integer, Totem> totens = new HashMap<>();

    private static final HashMap<Integer, List<Tranca>> trancasByTotemId = new HashMap<>();

    private static final HashMap<Integer, List<Bicicleta>> bicicletasByTotemId = new HashMap<>();
    private final IdGenerator id;

    public TotemRepository(IdGenerator id){
        this.id = id;
    }

    public Totem save(Totem totem){
        if(findById(totem.getId()).isPresent()){
            totens.replace(totem.getId(), totem);
            return totem;
        }
        Integer idTotem = id.idTotemGenerator();
        totem.setId(idTotem);
        totens.put(idTotem, totem);
        return totem;
    }

    public List<Totem> findAll(){
        return (List<Totem>) totens.values();
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

    public void addTrancasByTotemId(Integer idTotem, Tranca tranca){
        List<Tranca> trancas = trancasByTotemId.get(idTotem);
        if(trancas == null){
            trancasByTotemId.put(idTotem, new ArrayList<>());
            trancas = new ArrayList<>();
            trancasByTotemId.put(idTotem, trancas);
            trancas.add(tranca);
        }
        else
            trancas.add(tranca);
    }

    public boolean removeTrancaByTotemId(Integer idTotem, Tranca tranca){
        List<Tranca> trancas = trancasByTotemId.get(idTotem);
        if(trancas == null){
            return false;
        }
        trancas.remove(tranca);
        if(trancas.isEmpty()){
            trancasByTotemId.remove(idTotem);
        }
        return true;
    }

    public void addBicicletasByTotemId(Integer idTotem, Bicicleta bicicleta){
        List<Bicicleta> bicicletas = bicicletasByTotemId.get(idTotem);
        if(bicicletas == null){
            bicicletasByTotemId.put(idTotem, new ArrayList<>());
            bicicletas = new ArrayList<>();
            bicicletasByTotemId.put(idTotem, bicicletas);
            bicicletas.add(bicicleta);
        }
        else
            bicicletas.add(bicicleta);
    }

    public boolean removeBicicletaByTotemId(Integer idTotem, Bicicleta bicicleta){
        List<Bicicleta> bicicletas = bicicletasByTotemId.get(idTotem);
        if(bicicletas == null){
            return false;
        }
        bicicletas.remove(bicicleta);
        if(bicicletas.isEmpty()){
            bicicletasByTotemId.remove(idTotem);
        }
        return true;
    }

    public List<Tranca> findTrancasByTotemId(Integer idTotem){
        return trancasByTotemId.get(idTotem);
    }

    public Integer findTotemByTranca(Tranca trancaBuscada){
        for(Map.Entry<Integer, List<Tranca>> entry: trancasByTotemId.entrySet()){
            Integer idTotem = entry.getKey();
            List<Tranca> trancas = entry.getValue();
            if(trancas.contains(trancaBuscada)){
                return idTotem;
            }
        }
        return null;
    }

    public List<Bicicleta> findBicicletasByTotemId(Integer idTotem){
        return bicicletasByTotemId.get(idTotem);
    }
}
