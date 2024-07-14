package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TrancaRepository {

    private static HashMap<Integer, Tranca> trancas;

    private static HashMap<Integer, Bicicleta> bicicletaByTrancaId;

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

    public Tranca deleteById(Integer idTranca){
        if(findById(idTranca).isPresent()){
            return trancas.remove(idTranca);
        }
        return null;
    }

    public void addBicicletaByTrancaId(Integer idTranca, Bicicleta bicicleta){
        bicicletaByTrancaId.put(idTranca, bicicleta);
    }

    public void removeBicicletaByTrancaId(Integer idTranca, Bicicleta bicicleta){
        bicicletaByTrancaId.remove(idTranca, bicicleta);
    }

    public Bicicleta findBicicletaByTrancaId(Integer idTranca){
        return bicicletaByTrancaId.get(idTranca);
    }

    public Tranca postStatus(Integer idTranca, StatusTrancaEnum acao){
        Tranca tranca = findById(idTranca).orElseThrow(
                () -> new NotFoundException("Tranca não encontrada", HttpStatus.NOT_FOUND.toString()));

        switch (acao){
            case DESTRANCAR:
                tranca.setStatus("DESTRANCAR");
                break;
            case TRANCAR:
                tranca.setStatus("TRANCAR");
                break;
            default:
                throw new NotFoundException("Status não escolhido", HttpStatus.NOT_FOUND.toString());
        }
        save(tranca);
        return tranca;
    }
}
