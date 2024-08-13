package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TotemService {

    private final TotemRepository repository;

    private String totemMens = "Totem não existe";

    @Autowired
    public TotemService(TotemRepository repository) {
        this.repository = repository;
    }

    public List<Totem> getAll() {
        return repository.findAll();
    }

    public Totem save(Totem totem) {
        if(validateTotem(totem)){
            throw new InvalidActionException("Dados do totem inválidos");
        }
        return repository.save(totem);
    }

    private boolean validateTotem(Totem totem){
        return totem.getDescricao() == null || totem.getLocalizacao() == null;
    }

    public Totem getById(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(totemMens));
    }

    public Totem updateTotem(Integer idTotem, Totem novoTotem){
        Totem totemAtualizado = getById(idTotem);
        if(totemAtualizado.getId() == null) {
            throw new NotFoundException(totemMens);
        }
        if(validateTotem(novoTotem)){
            throw new InvalidActionException("Dados do totem inválidos");
        }

            totemAtualizado.setDescricao(novoTotem.getDescricao());
            totemAtualizado.setLocalizacao(novoTotem.getLocalizacao());

            return repository.save(totemAtualizado);
        }

    public void deleteTotem(Integer idTotem){
        if(!repository.deleteById(idTotem)){
            throw new NotFoundException("Totem não encontrado");
        }
    }

    public List<Tranca> getTrancasByTotem(Integer idTotem){
        Totem totem = getById(idTotem);
        if(totem == null){
            throw new NotFoundException(totemMens);
        }
        return repository.findTrancasByTotemId(idTotem);
    }

    public List<Bicicleta> getBicicletasByTotem(Integer idTotem){
        Totem totem = getById(idTotem);
        if(totem == null){
            throw new NotFoundException(totemMens);
        }
        return repository.findBicicletasByTotemId(idTotem);
    }
}
