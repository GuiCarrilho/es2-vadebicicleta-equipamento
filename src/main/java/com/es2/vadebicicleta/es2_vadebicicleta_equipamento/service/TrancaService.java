package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
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

    @Autowired
    public TrancaService(TrancaRepository repository, BicicletaService bicicletaService) {
        this.repository = repository;
        this.bicicletaService = bicicletaService;
    }

    public Tranca save(Tranca tranca) {
        return repository.save(tranca);
    }

    public List<Tranca> getAll() {
        return repository.findAll();
    }

    public Tranca getById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Tranca não existe", HttpStatus.NOT_FOUND.toString()));
    }

    public Tranca updateTranca(Integer idTranca, TrancaDto novaTranca) {

        Tranca trancaAtualizada = getById(idTranca);

        trancaAtualizada.setNumero(novaTranca.getNumero());
        trancaAtualizada.setModelo(novaTranca.getModelo());
        trancaAtualizada.setStatus(novaTranca.getStatus());
        trancaAtualizada.setAnoDeFabricacao(novaTranca.getAnoDeFabricacao());
        trancaAtualizada.setLocalizacao(novaTranca.getLocalizacao());
        Bicicleta bicicleta = bicicletaService.getById(novaTranca.getBicicletaId());
        trancaAtualizada.setBicicleta(bicicleta);

        return repository.save(trancaAtualizada);
    }

    public Tranca deleteTranca(Integer idTranca) {
        return repository.deleteById(idTranca);
    }

    public Bicicleta getBicicletaByTranca(Integer idTranca) {
        return repository.findBicicletaByTrancaId(idTranca);
    }

    public Tranca postStatus(Integer idTranca, StatusTrancaEnum acao){
        return repository.postStatus(idTranca, acao);
    }

    public Tranca trancar(Integer idTranca, Integer idBicicleta){
        Tranca tranca = getById(idTranca);
        if(tranca == null){
            throw new NotFoundException("Tranca não encontrada", HttpStatus.NOT_FOUND.toString());
        }
        if(Objects.equals(tranca.getStatus(), "TRANCAR")){
            throw new NotFoundException("Tranca não encontrada", HttpStatus.NOT_FOUND.toString());
        }
        tranca.setStatus("TRANCAR");
        repository.save(tranca);
        if(idBicicleta != null) {
            Bicicleta bicicleta = bicicletaService.getById(idBicicleta);
            if(bicicleta == null){
                throw new NotFoundException("Bicicleta não encontrada", HttpStatus.NOT_FOUND.toString());
            }
            bicicleta.setStatus("DISPONIVEL");
            bicicletaService.save(bicicleta);
            repository.addBicicletaByTrancaId(idTranca, bicicleta);
            return tranca;
        }
        return tranca;
    }
}

