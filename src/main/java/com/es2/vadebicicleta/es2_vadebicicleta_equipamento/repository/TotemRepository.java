package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;

import org.springframework.stereotype.Repository;

import java.util.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TotemRepository {

    private static final HashMap<Integer, Totem> totens = new HashMap<>();
    private static final HashMap<Integer, List<Tranca>> trancasByTotemId = new HashMap<>();

    private final IdGenerator id;
    private final BicicletaRepository bicicletaRepository;

    public TotemRepository(IdGenerator id, BicicletaRepository bicicletaRepository){
        this.id = id;
        this.bicicletaRepository = bicicletaRepository;
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
        return new ArrayList<>(totens.values());
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

    public List<Tranca> findTrancasByTotemId(Integer idTotem){
        return trancasByTotemId.getOrDefault(idTotem, new ArrayList<>());
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
        List<Bicicleta> bicicletas = new ArrayList<>();
        
        List<Tranca> trancas = trancasByTotemId.get(idTotem);
        if (trancas == null) {
            throw new NotFoundException("Nenhuma bicicleta na rede de totens");
        }
        
        for (Tranca tranca : trancas) {
            Integer idBicicleta = tranca.getBicicleta();
            if (idBicicleta != null) {
                Bicicleta bicicleta = bicicletaRepository.findById(idBicicleta).orElseThrow(
                () -> new NotFoundException("Bicicleta não encontrada"));
                if (bicicleta != null) {
                    bicicletas.add(bicicleta);
                }
            }
        }
        
        return bicicletas;
    }
}
