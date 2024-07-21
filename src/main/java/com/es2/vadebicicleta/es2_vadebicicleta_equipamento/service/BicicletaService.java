package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicicletaService {

    private final BicicletaRepository repository;

    @Autowired
    public BicicletaService(BicicletaRepository repository) {
        this.repository = repository;
    }


    public Bicicleta save(Bicicleta bicicleta) {
        if(validateBicicleta(bicicleta)){
            throw new InvalidActionException(HttpStatus.UNPROCESSABLE_ENTITY.toString(), "Dados da bicicleta inválidos");
        }
        return repository.save(bicicleta);
    }

    private boolean validateBicicleta(Bicicleta bicicleta){
        if(bicicleta.getAno() == null || bicicleta.getNumero() == null || bicicleta.getModelo() == null || bicicleta.getMarca() == null || bicicleta.getStatus() == null){
            return false;
        }
        return true;
    }

    public List<Bicicleta> getAll() {
        return repository.findAll();
    }

    public Bicicleta getById(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Bicicleta não existe"));
    }

    public Bicicleta updateBicicleta(Integer idBicicleta, Bicicleta bicicletaNova){
        Bicicleta bicicletaAtualizada = getById(idBicicleta);
        if(bicicletaAtualizada.getId() == null){
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Bicicleta não existe");
        }
        if(!validateBicicleta(bicicletaAtualizada)){
            throw new InvalidActionException(HttpStatus.UNPROCESSABLE_ENTITY.toString(), "Dados da bicicleta inválidos");
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
            throw new NotFoundException(HttpStatus.NOT_FOUND.toString(), "Bicicleta não encontrada");
        }
    }

    public Bicicleta postStatus(Integer idBicicleta, StatusBicicletaEnum acao){
        return repository.postStatus(idBicicleta, acao);
    }
}

