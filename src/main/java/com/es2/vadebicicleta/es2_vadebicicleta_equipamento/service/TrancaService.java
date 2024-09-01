package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.EnderecoEmail;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Funcionario;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao.AluguelClient;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao.ExternoClient;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TrancaService {

    private final TrancaRepository repository;
    private final BicicletaService bicicletaService;
    private final TotemRepository totemRepository;
    private final AluguelClient aluguelClient;
    private final ExternoClient externoClient;

    private String trancaErro = "Tranca não encontrada";
    private String bicicletaErro = "Bicicleta não encontrada";
    private String livreMens = "LIVRE";
    private String ocuparMens = "OCUPADA";
    private String aposentada = "APOSENTADA";
    private String emReparo = "EM_RAPARO";

    @Autowired
    public TrancaService(TrancaRepository repository, BicicletaService bicicletaService, TotemRepository totemRepository, AluguelClient aluguelClient, ExternoClient externoClient) {
        this.repository = repository;
        this.bicicletaService = bicicletaService;
        this.totemRepository = totemRepository;
        this.aluguelClient = aluguelClient;
        this.externoClient = externoClient;
    }

     public Tranca save(Tranca tranca) {
        if(validateTranca(tranca)){
            throw new InvalidActionException("Dados da tranca inválidos");
        }
        return repository.save(tranca);
    }

    private boolean validateTranca(Tranca tranca){
        if(tranca.getLocalizacao() == null || tranca.getLocalizacao().isEmpty()){
            return true;
        }
        if(tranca.getModelo() == null || tranca.getModelo().isEmpty()){
            return true;
        }
        if(tranca.getAnoDeFabricacao() == null || tranca.getAnoDeFabricacao().isEmpty()){
            return true;
        }
        if(tranca.getStatus() == null || tranca.getStatus().isEmpty()){
            return true;
        }
        if(tranca.getId() == null && !tranca.getStatus().equals("NOVA")){
            return true;
        }
        return tranca.getNumero() == null;
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
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException("Id da tranca inválido"));
        if(tranca.getBicicleta() != 0){
            throw new InvalidActionException("Tranca está associada a uma bicicleta");
        }
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

        if(acao == null){
            throw new InvalidActionException("Status inválido");
        }

        switch (acao){
            case LIVRE:
                tranca.setStatus(livreMens);
                break;
            case OCUPADA:
                tranca.setStatus(ocuparMens);
                break;
            case APOSENTADA:
                tranca.setStatus(aposentada);
                break;
            case NOVA:
                tranca.setStatus("NOVA");
                break;
            case EM_REPARO:
                tranca.setStatus(emReparo);
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
        if (Objects.equals(tranca.getStatus(), ocuparMens)) {
            throw new NotFoundException("Dados inválidos ou tranca já se encontra ocupada");
        }
        tranca.setStatus(ocuparMens);
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
        if (Objects.equals(tranca.getStatus(), livreMens)) {
            throw new InvalidActionException("Dados inválidos ou tranca já se encontra livre");
        }
        tranca.setStatus(livreMens);
        repository.save(tranca);
        if (idBicicleta != null) {
            Bicicleta bicicleta = bicicletaService.getById(idBicicleta);
            if (bicicleta == null) {
                throw new NotFoundException(bicicletaErro);
            }
            bicicleta.setStatus("REPARO_SOLICITADO");
            bicicletaService.save(bicicleta);
            tranca.setBicicleta(0);
            repository.save(tranca);
            return tranca;
        }
        return tranca;
    }

    private void enviarEmailInclusao(Funcionario funcionario, Tranca tranca){
        EnderecoEmail email = new EnderecoEmail();
        email.setAssunto("Inclusão da bicicleta na rede");
        email.setMensagem("Data/Hora da Inserção: " + tranca.getDataHoraInsRet() + "\nId do Funcionário: " + funcionario.getId() + "\nNúmero da Tranca: " + tranca.getNumero());
        email.setEmail(funcionario.getEmail());
        externoClient.enviarEmail(email);
    }

    private void enviarEmailRemocao(Funcionario funcionario, Tranca tranca){
        EnderecoEmail email = new EnderecoEmail();
        email.setAssunto("Inclusão da bicicleta na rede");
        email.setMensagem("Data/Hora da Remoção: " + tranca.getDataHoraInsRet() + "\nId do Funcionário: " + funcionario.getId() + "\nNúmero da Tranca: " + tranca.getNumero());
        email.setEmail(funcionario.getEmail());
        externoClient.enviarEmail(email);
    }

    public void incluirTrancaNaRedeTotem(Integer idTotem, Integer idTranca, Integer idFuncionario){
        Totem totem = totemRepository.findById(idTotem).orElseThrow(
                () -> new InvalidActionException("Totem não encontrado"));
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException(trancaErro));
        Funcionario funcionario = aluguelClient.obterFuncionario(idFuncionario);
        Integer idTotemValidado = totemRepository.findTotemByTranca(tranca);
        if(idTotem.equals(idTotemValidado)){
            throw new InvalidActionException("Tranca já associada ao totem");
        }
        if(!tranca.getStatus().equals("NOVA") && !tranca.getStatus().equals(emReparo)){
            throw new InvalidActionException("Status da tranca inválida");
        }
        if(Objects.equals(tranca.getStatus(), "NOVA")){
            totemRepository.addTrancasByTotemId(totem.getId(), tranca);
            tranca.setDataHoraInsRet(LocalDateTime.now());
        }
        if(Objects.equals(tranca.getStatus(), emReparo)){
            if(!tranca.getFuncionario().equals(idFuncionario)){
                throw new InvalidActionException("Funcionário não é o mesmo que a colocou em reparo");
            }
            totemRepository.addTrancasByTotemId(totem.getId(), tranca);
            tranca.setDataHoraInsRet(LocalDateTime.now());
        }
        tranca.setStatus(livreMens);
        enviarEmailInclusao(funcionario, tranca);
    }

    public void retirarTrancaDaRedeTotem(Integer idTotem, Integer idTranca, Integer idFuncionario, String statusAcaoReparador){
        Totem totem = totemRepository.findById(idTotem).orElseThrow(
                () -> new InvalidActionException("Totem não encontrado"));
        Tranca tranca = repository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException(trancaErro));
        if(!tranca.getBicicleta().equals(0)){
            throw new InvalidActionException("Tranca está associada a uma bicicleta");
        }
        Funcionario funcionario = aluguelClient.obterFuncionario(idFuncionario);
        Integer idTotemValidado = totemRepository.findTotemByTranca(tranca);
        if (idTotemValidado == null || !idTotemValidado.equals(idTotem)) {
            throw new InvalidActionException("Tranca já se encontra desassociada do totem");
        }
        if(!statusAcaoReparador.equals(aposentada) && !statusAcaoReparador.equals(emReparo)){
            throw new InvalidActionException("Status da ação do reparador inválido");
        }
        if(Objects.equals(statusAcaoReparador, aposentada)){
            tranca.setStatus(statusAcaoReparador);
            boolean removido = totemRepository.removeTrancaByTotemId(totem.getId(), tranca);
            if(!removido){
                throw new InvalidActionException("Tranca não está associada ao totem");
            }
            tranca.setDataHoraInsRet(LocalDateTime.now());
        }
        if(Objects.equals(statusAcaoReparador, emReparo)){
            tranca.setStatus(statusAcaoReparador);
            boolean removido = totemRepository.removeTrancaByTotemId(totem.getId(), tranca);
            if(!removido){
                throw new InvalidActionException("Tranca não está associada ao totem");
            }
            tranca.setDataHoraInsRet(LocalDateTime.now());
            tranca.setFuncionario(idFuncionario);
        }
        enviarEmailRemocao(funcionario, tranca);
    }
}


