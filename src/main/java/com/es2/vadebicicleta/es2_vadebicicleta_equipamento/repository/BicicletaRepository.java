package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.sound.midi.InvalidMidiDataException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusEnum.DISPONIVEL;

@Repository
public class BicicletaRepository {

    private static HashMap<Integer, Bicicleta> bicicletas;
    private IdGenerator id;

    public Bicicleta save(Bicicleta bicicleta){
        if (bicicleta.getId() == null) {
            bicicleta.setId(id.geradorId());
        }
        bicicletas.replace(bicicleta.getId(), bicicleta);
        return bicicleta;
    }
    public List<Bicicleta> findAll(){
        return (List<Bicicleta>) bicicletas.values();
    }

    public Optional<Bicicleta> findById(Integer id){
        return Optional.ofNullable(bicicletas.get(id));
    }

    public Bicicleta deleteById(Integer idBicicleta){
        if(findById(idBicicleta).isPresent()){
            return bicicletas.remove(idBicicleta);
        }
        return null;
    }

    public Bicicleta postStatus(Integer idBicicleta, StatusEnum acao){
        Bicicleta bicicleta = findById(idBicicleta).orElseThrow(
                () -> new NotFoundException("Bicicleta não encontrada", HttpStatus.NOT_FOUND.toString()));

        switch (acao){
            case DISPONIVEL:
                bicicleta.setStatus("DISPONIVEL");
                break;
            case EM_USO:
                bicicleta.setStatus("EM_USO");
                break;
            case NOVA:
                bicicleta.setStatus("NOVA");
                break;
            case APOSENTADA:
                bicicleta.setStatus("APOSENTADA");
                break;
            case REPARO_SOLICITADO:
                bicicleta.setStatus("REPARAO_SOLICITADO");
                break;
            case EM_REPARO:
                bicicleta.setStatus("EM_REPARO");
                break;
            default:
                throw new NotFoundException("Status não escolhido", HttpStatus.NOT_FOUND.toString());
        }
        save(bicicleta);
        return bicicleta;
    }
}
