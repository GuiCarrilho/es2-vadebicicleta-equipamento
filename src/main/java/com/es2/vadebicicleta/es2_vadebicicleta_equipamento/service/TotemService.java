package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TotemService {

    private final TotemRepository repository;

    @Autowired
    public TotemService(TotemRepository repository) {
        this.repository = repository;
    }

    public List<Totem> getAll() {
        return repository.findAll();
    }

    public Totem save(Totem totem) {
        return repository.save(totem);
    }

    public Optional<Totem> getById(Integer id){
        return repository.findById(id);
    }

    public Totem updateTotem(Integer idTotem, Totem novoTotem){
        Optional<Totem> totem = getById(idTotem);
        if(totem.isPresent()){
            Totem totemAtualizado = totem.get();
            totemAtualizado.setDescricao(novoTotem.getDescricao());
            totemAtualizado.setLocalizacao(novoTotem.getLocalizacao());

            return repository.save(totemAtualizado);
        }
        return null;
    }

    public Totem deleteTotem(Integer idTotem){
        return repository.deleteById(idTotem);
    }
}
