package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class TrancaService {

    private final TrancaRepository repository;
    private final BicicletaService bicicletaService;
    private final TotemRepository totemRepository;
    private String bicicletaErro = "Bicicleta não encontrada";
    private String trancaErro = "Tranca não encontrada";
    private String destrancarMens = "DESTRANCAR";
    private String trancarMens = "TRANCAR";

    @Autowired
    public TrancaService(TrancaRepository repository, BicicletaService bicicletaService, TotemRepository totemRepository) {
        this.repository = repository;
        this.bicicletaService = bicicletaService;
        this.totemRepository = totemRepository;
    }

     public Tranca save(Tranca tranca) {
        if(validateTranca(tranca)){
            throw new InvalidActionException("Dados da tranca inválidos");
        }
        return repository.save(tranca);
    }

    private boolean validateTranca(Tranca tranca){
        return tranca.getLocalizacao() == null || tranca.getNumero() == null || tranca.getModelo() == null || tranca.getAnoDeFabricacao() == null || tranca.getStatus() == null;
    }

    public List<Tranca> getAll() {
        return repository.findAll();
    }

    public Tranca getById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(trancaErro));
    }

    public Tranca updateTranca(Integer idTranca, Tranca novaTranca) {
        Tranca trancaAtualizada = getById(idTranca);
        if(trancaAtualizada.getId() == null){
            throw new NotFoundException(trancaErro);
        }
        if(validateTranca(novaTranca)){
            throw new InvalidActionException("Dados da tranca inválidos");
        }

        trancaAtualizada.setNumero(novaTranca.getNumero());
        trancaAtualizada.setModelo(novaTranca.getModelo());
        trancaAtualizada.setStatus(novaTranca.getStatus());
        trancaAtualizada.setAnoDeFabricacao(novaTranca.getAnoDeFabricacao());
        trancaAtualizada.setLocalizacao(novaTranca.getLocalizacao());


        return repository.save(trancaAtualizada);
    }

    public void deleteTotem(Integer idTranca){
        if(!repository.deleteById(idTranca)){
            throw new NotFoundException(trancaErro);
        }
    }
}


