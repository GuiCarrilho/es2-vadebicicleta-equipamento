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

    private String trancaErro = "Tranca não encontrada";
    private String bicicletaErro = "Bicicleta não encontrada";
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

    public void deleteTranca(Integer idTranca){
        if(!repository.deleteById(idTranca)){
            throw new NotFoundException(trancaErro);
        }
    }

    public Bicicleta getBicicletaByTrancaId(Integer idTranca) {
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException("Id da tranca inválido"));
        Integer idBicicleta = tranca.getBicicleta();
        if(idBicicleta == null){
            throw new NotFoundException(bicicletaErro);
        }
        return bicicletaService.getById(idBicicleta);
    }

    public Tranca postStatus(Integer idTranca, StatusTrancaEnum acao){
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new NotFoundException(trancaErro));

        switch (acao){
            case DESTRANCAR:
                tranca.setStatus(destrancarMens);
                break;
            case TRANCAR:
                tranca.setStatus(trancarMens);
                break;
            case APOSENTADA:
                tranca.setStatus("APOSENTADA");
                break;
            case NOVA:
                tranca.setStatus("NOVA");
                break;
            case EM_REPARO:
                tranca.setStatus("EM_REPARO");
                break;
            default:
                throw new InvalidActionException("Status não escolhido");
        }
        repository.save(tranca);
        return tranca;
    }

    public Tranca trancar(Integer idTranca, Integer idBicicleta) {
        Tranca tranca = getById(idTranca);
        if (tranca == null) {
            throw new NotFoundException(trancaErro);
        }
        if(tranca.getBicicleta() == idBicicleta){
            throw new InvalidActionException("Bicicleta já está associada a tranca");
        }
        if (Objects.equals(tranca.getStatus(), trancarMens)) {
            throw new NotFoundException("Dados inválidos ou tranca já se encontra trancada");
        }
        tranca.setStatus(trancarMens);
        repository.save(tranca);
        if (idBicicleta != null) {
            Bicicleta bicicleta = bicicletaService.getById(idBicicleta);
            if (bicicleta == null) {
                throw new NotFoundException(bicicletaErro);
            }
            bicicleta.setStatus("DISPONIVEL");
            bicicletaService.save(bicicleta);
            tranca.setBicicleta(idBicicleta);
            repository.save(tranca);
            return tranca;
        }
        return tranca;
    }

    public Tranca destrancar(Integer idTranca, Integer idBicicleta) {
        Tranca tranca = getById(idTranca);
        if (tranca == null) {
            throw new NotFoundException(trancaErro);
        }
        if(tranca.getBicicleta() != idBicicleta){
            throw new InvalidActionException("Bicicleta já está disassociada de tranca");
        }
        if (Objects.equals(tranca.getStatus(), destrancarMens)) {
            throw new NotFoundException("Dados inválidos ou tranca já se encontra destrancada");
        }
        tranca.setStatus(destrancarMens);
        repository.save(tranca);
        if (idBicicleta != null) {
            Bicicleta bicicleta = bicicletaService.getById(idBicicleta);
            if (bicicleta == null) {
                throw new NotFoundException(bicicletaErro);
            }
            bicicleta.setStatus("EM_USO");
            bicicletaService.save(bicicleta);
            tranca.setBicicleta(null);
            repository.save(tranca);
            return tranca;
        }
        return tranca;
    }

    public void incluirTrancaNaRedeTotem(Integer idTotem, Integer idTranca, Integer idFuncionario){
        Totem totem = totemRepository.findById(idTotem).orElseThrow(
                () -> new InvalidActionException("Totem não encontrado"));
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException(trancaErro));
        if(idFuncionario == null){
            throw new InvalidActionException("Funcionário não existe");
        }
        if(Objects.equals(tranca.getStatus(), "NOVA") || Objects.equals(tranca.getStatus(), destrancarMens)){
            totemRepository.addTrancasByTotemId(totem.getId(), tranca);
        }
        else throw new InvalidActionException("Status da tranca inválido");
    }

    public void retirarTrancaDaRedeTotem(Integer idTotem, Integer idTranca, Integer idFuncionario, String statusAcaoReparador){
        Totem totem = totemRepository.findById(idTotem).orElseThrow(
                () -> new InvalidActionException("Totem não encontrado"));
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException(trancaErro));
        if(idFuncionario == null){
            throw new InvalidActionException("Funcionário não existe");
        }
        if(Objects.equals(statusAcaoReparador, "APOSENTADA") || Objects.equals(statusAcaoReparador, "EM_REPARO")){
            tranca.setStatus(statusAcaoReparador);
            boolean removido = totemRepository.removeTrancaByTotemId(totem.getId(), tranca);
            if(!removido){
                throw new InvalidActionException("Tranca não está associada ao totem");
            }
        }
        else throw new InvalidActionException("Status da tranca inválido");
    }
}


