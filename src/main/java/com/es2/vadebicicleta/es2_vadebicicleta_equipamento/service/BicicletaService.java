package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BicicletaService {

    private final BicicletaRepository repository;

    private final TrancaRepository trancaRepository;

    private final TotemRepository totemRepository;

    @Autowired
    public BicicletaService(BicicletaRepository repository, TrancaRepository trancaRepository, TotemRepository totemRepository) {
        this.repository = repository;
        this.trancaRepository = trancaRepository;
        this.totemRepository = totemRepository;
    }


    public Bicicleta save(Bicicleta bicicleta) {
        if(validateBicicleta(bicicleta)){
            throw new InvalidActionException("Dados da bicicleta inválidos");
        }
        return repository.save(bicicleta);
    }

    private boolean validateBicicleta(Bicicleta bicicleta){
        return bicicleta.getAno() == null || bicicleta.getNumero() == null || bicicleta.getModelo() == null || bicicleta.getMarca() == null || bicicleta.getStatus() == null;
    }

    public List<Bicicleta> getAll() {
        return repository.findAll();
    }

    public Bicicleta getById(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Bicicleta não existe"));
    }

    public Bicicleta updateBicicleta(Integer idBicicleta, Bicicleta bicicletaNova){
        Bicicleta bicicletaAtualizada = getById(idBicicleta);
        if(bicicletaAtualizada.getId() == null){
            throw new NotFoundException("Bicicleta não existe");
        }
        if(validateBicicleta(bicicletaNova)){
            throw new InvalidActionException("Dados da bicicleta inválidos");
        }
            bicicletaAtualizada.setAno(bicicletaNova.getAno());
            bicicletaAtualizada.setMarca(bicicletaNova.getMarca());
            bicicletaAtualizada.setStatus(bicicletaNova.getStatus());
            bicicletaAtualizada.setNumero(bicicletaNova.getNumero());
            bicicletaAtualizada.setModelo(bicicletaNova.getModelo());

            return repository.save(bicicletaAtualizada);
    }

    public void deleteBicicleta(Integer idBicicleta){

        if(!repository.deleteById(idBicicleta)){
            throw new NotFoundException("Bicicleta não encontrada");
        }
    }

    public Bicicleta postStatus(Integer idBicicleta, StatusBicicletaEnum acao){
        Bicicleta bicicleta = repository.findById(idBicicleta).orElseThrow(
                () -> new NotFoundException("Bicicleta não encontrada"));

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
                throw new InvalidActionException("Status não escolhido");
        }
        repository.save(bicicleta);
        return bicicleta;
    }

    public void incluirBicicletaNaRedeTotem(Integer idTranca, Integer idBicicleta, Integer idFuncionario){
        Tranca tranca = trancaRepository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException("Tranca não encontrada"));
        if(!Objects.equals(tranca.getBicicleta(), idBicicleta)){
            throw new InvalidActionException("Id da bicicleta na classe Tranca não corresponde ao parâmetro");
        }
        if(idFuncionario == null){
            throw new InvalidActionException("Funcionário não existe");
        }
        Integer idTotem = totemRepository.findTotemByTranca(tranca);
        if(idTotem == null){
            throw new InvalidActionException("Tranca não está relacionada com nenhum totem");
        }
        Bicicleta bicicleta = getById(idBicicleta);
        if(Objects.equals(bicicleta.getStatus(), "NOVA") || Objects.equals(bicicleta.getStatus(), "DISPONIVEL")){
            totemRepository.addBicicletasByTotemId(idTotem, bicicleta);
        }
        else throw new InvalidActionException("Status de bicicleta inválido");
    }

    public void retirarBicicletaDaRedeTotem(Integer idTranca, Integer idBicicleta, Integer idFuncionario, String statusAcaoReparador){
        Tranca tranca = trancaRepository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException("Tranca não encontrada"));
        if(!Objects.equals(tranca.getBicicleta(), idBicicleta)){
            throw new InvalidActionException("Id da bicicleta na classe Tranca não corresponde ao parâmetro");
        }
        if(idFuncionario == null){
            throw new InvalidActionException("Funcionário não existe");
        }
        Integer idTotem = totemRepository.findTotemByTranca(tranca);
        if(idTotem == null){
            throw new InvalidActionException("Tranca não está relacionada com nenhum totem");
        }
        Bicicleta bicicleta = getById(idBicicleta);
        if(Objects.equals(statusAcaoReparador, "APOSENTADA") || Objects.equals(statusAcaoReparador, "REPARO_SOLICITADO")){
            bicicleta.setStatus(statusAcaoReparador);
            boolean removido = totemRepository.removeBicicletaByTotemId(idTotem, bicicleta);
            if(!removido){
                throw new InvalidActionException("Bicicleta não está associada ao totem");
            }
        }
        else throw new InvalidActionException("Status de bicicleta inválido");
    }
}


