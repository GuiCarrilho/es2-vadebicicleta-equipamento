package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.EnderecoEmail;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Funcionario;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao.AluguelClient;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao.ExternoClient;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BicicletaService {

    private final BicicletaRepository repository;

    private final TrancaRepository trancaRepository;

    private final TrancaService trancaService;

    private final TotemRepository totemRepository;

    private ExternoClient externoClient;

    private AluguelClient aluguelClient;

    @Autowired
    public BicicletaService(BicicletaRepository repository, TrancaRepository trancaRepository, TrancaService trancaService, TotemRepository totemRepository, ExternoClient externoClient, AluguelClient aluguelClient) {
        this.repository = repository;
        this.trancaRepository = trancaRepository;
        this.trancaService = trancaService;
        this.totemRepository = totemRepository;
        this.externoClient = externoClient;
        this.aluguelClient = aluguelClient;
    }


    public Bicicleta save(Bicicleta bicicleta) {
        if(validateBicicleta(bicicleta)){
            throw new InvalidActionException("Dados da bicicleta inválidos");
        }
        return repository.save(bicicleta);
    }

    private boolean validateBicicleta(Bicicleta bicicleta){
        if(bicicleta.getAno() == null || bicicleta.getAno().isEmpty()){
            return true;
        }
        if(bicicleta.getModelo() == null || bicicleta.getModelo().isEmpty()){
            return true;
        }  
        if(bicicleta.getMarca() == null || bicicleta.getMarca().isEmpty()){
            return true;
        }   
        if(bicicleta.getStatus() == null || bicicleta.getStatus().isEmpty()){
            return true;
        }
        if(bicicleta.getId() == null && !bicicleta.getStatus().equals("NOVA")){
            return true;
        }
        return bicicleta.getNumero() == null;
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
        Bicicleta bicicleta = repository.findById(idBicicleta).orElseThrow(
            () -> new NotFoundException("Bicicleta não encontrada"));
        if(!bicicleta.getStatus().equals("APOSENTADA")){
            throw new NotFoundException("Status Inválido");
        }
        if(!trancaRepository.findTrancaByBicicleta(idBicicleta)){
            throw new NotFoundException("Bicicleta não está associda a nenhuma tranca");
        }
        if(!repository.deleteById(idBicicleta)){
            throw new NotFoundException("Bicicleta não encontrada");
        }
    }

    public Bicicleta postStatus(Integer idBicicleta, StatusBicicletaEnum acao){
        Bicicleta bicicleta = repository.findById(idBicicleta).orElseThrow(
                () -> new NotFoundException("Bicicleta não encontrada"));
        
        if(acao == null){
            throw new InvalidActionException("Status inválido");
        }

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
                bicicleta.setStatus("REPARO_SOLICITADO");
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


    private void enviarEmailInclusao(Funcionario funcionario, Bicicleta bicicleta, Tranca tranca){
        EnderecoEmail email = new EnderecoEmail();
        email.setAssunto("Inclusão da bicicleta na rede");
        email.setMensagem("Data/Hora da Inserção: " + bicicleta.getDataHoraInsRet() + "\nNúmero da Bicicleta: " + bicicleta.getNumero() + "\nNúmero da Tranca: " + tranca.getNumero());
        email.setEmail(funcionario.getEmail());
        externoClient.enviarEmail(email);
    }

    private void enviarEmailRemocao(Funcionario funcionario, Bicicleta bicicleta, Tranca tranca){
        EnderecoEmail email = new EnderecoEmail();
        email.setAssunto("Remoção da bicicleta da rede");
        email.setMensagem("Data/Hora da Remoção: " + bicicleta.getDataHoraInsRet() + "\nNúmero da Bicicleta: " + bicicleta.getNumero() + "\nNúmero da Tranca: " + tranca.getNumero());
        email.setEmail(funcionario.getEmail());
        externoClient.enviarEmail(email);
    }

    public void incluirBicicletaNaRedeTotem(Integer idTranca, Integer idBicicleta, Integer idFuncionario){
        Tranca tranca = trancaRepository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException("Tranca não encontrada"));
        if(Objects.equals(tranca.getBicicleta(), idBicicleta)){
            throw new InvalidActionException("Id da bicicleta na classe Tranca corresponde ao id passado como parâmetro");
        }
        if(tranca.getBicicleta() != 0){
            throw new InvalidActionException("Tranca já está associada a uma bicicleta");
        }
        if(!tranca.getStatus().equals("LIVRE")){
            throw new InvalidActionException("Status de tranca inválido");
        }
        Funcionario funcionario = aluguelClient.obterFuncionario(idFuncionario);
        Integer idTotem = totemRepository.findTotemByTranca(tranca);
        if(idTotem == null){
            throw new InvalidActionException("Tranca não está relacionada com nenhum totem");
        }
        Bicicleta bicicleta = repository.findById(idBicicleta).orElseThrow(
            () -> new InvalidActionException("Bicicleta não encontrada"));
        if(Objects.equals(bicicleta.getStatus(), "NOVA")){
            tranca = trancaService.trancar(idTranca, idBicicleta);
            bicicleta.setDataHoraInsRet(LocalDateTime.now());
        }
        if(Objects.equals(bicicleta.getStatus(), "EM_REPARO")){
            if(!bicicleta.getFuncionario().equals(idFuncionario)){
                throw new InvalidActionException("Funcionário não é o mesmo que a colocou em reparo");
            }
            tranca = trancaService.trancar(idTranca, idBicicleta);
            bicicleta.setDataHoraInsRet(LocalDateTime.now());
        }
        enviarEmailInclusao(funcionario, bicicleta, tranca);
    }

    public void retirarBicicletaDaRedeTotem(Integer idTranca, Integer idBicicleta, Integer idFuncionario, String statusAcaoReparador){
        Tranca tranca = trancaRepository.findById(idTranca).orElseThrow(
                () -> new InvalidActionException("Tranca não encontrada"));
        if(!Objects.equals(tranca.getBicicleta(), idBicicleta)){
            throw new InvalidActionException("A bicicleta não está associada a uma tranca");
        }
        Funcionario funcionario = aluguelClient.obterFuncionario(idFuncionario);
        if(!tranca.getStatus().equals("OCUPADA")){
            throw new InvalidActionException("Status da tranca inválido");
        }
        Integer idTotem = totemRepository.findTotemByTranca(tranca);
        if(idTotem == null){
            throw new InvalidActionException("Tranca não está relacionada com nenhum totem");
        }
        Bicicleta bicicleta = repository.findById(idBicicleta).orElseThrow(
            () -> new InvalidActionException("Bicicleta não encontrada"));
        trancaService.destrancar(idTranca, idBicicleta);
        bicicleta.setDataHoraInsRet(LocalDateTime.now());
        if(!bicicleta.getStatus().equals("REPARO_SOLICITADO")){
            throw new InvalidActionException("Status da bicicleta inválido");
        }
        if(Objects.equals(statusAcaoReparador, "APOSENTADA")){
            bicicleta.setStatus(statusAcaoReparador);
        }
        if(Objects.equals(statusAcaoReparador, "EM_REPARO")){
            bicicleta.setStatus(statusAcaoReparador);
            bicicleta.setFuncionario(idFuncionario);
        }
        enviarEmailRemocao(funcionario, bicicleta, tranca):
    }
}


