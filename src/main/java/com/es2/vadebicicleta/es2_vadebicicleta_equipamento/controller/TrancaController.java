package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.Erro;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrancaController {

    private final TrancaService service;

    private final TrancaConverter converter;

    @Autowired
    public TrancaController(TrancaService service, TrancaConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/tranca")
    public ResponseEntity<List<Tranca>> getTrancas(){
        List<Tranca> trancas = service.getAll();
        if(trancas == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(trancas);
    }

    @PostMapping("/tranca")
    public ResponseEntity<?> postTranca(@RequestBody TrancaDto trancaDto){
        try {
            Tranca tranca = converter.dtoToEntity(trancaDto);
            Tranca trancaNova = service.save(tranca);
            return ResponseEntity.ok().body(trancaNova);
        }catch (InvalidActionException e){
            return ResponseEntity.status(422).body(new Erro("422", e.getMessage()));
        }
    }

    @GetMapping("/tranca/{idTranca}")
    public ResponseEntity<?> getTrancaById(@PathVariable Integer idTranca){
        try {
            Tranca tranca = service.getById(idTranca);
            return ResponseEntity.ok().body(tranca);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(new Erro("404", e.getMessage()));
        }
    }

    @PutMapping("/tranca/{idTranca}")
    public ResponseEntity<?> putTranca(@PathVariable Integer idTranca, @RequestBody TrancaDto trancaDto){
        try {
            Tranca novaTranca = converter.dtoToEntity(trancaDto);
            Tranca trancaAtualizada = service.updateTranca(idTranca, novaTranca);
            return ResponseEntity.ok().body(trancaAtualizada);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(new Erro("404", e.getMessage()));
        }catch (InvalidActionException e){
            return ResponseEntity.status(422).body(new Erro("422", e.getMessage()));
        }
    }

    @DeleteMapping("/tranca/{idTranca}")
    public ResponseEntity<?> deleteTranca(@PathVariable Integer idTranca) {
        try {
            service.deleteTranca(idTranca);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new Erro("404", e.getMessage()));
        }
    }

    @GetMapping("/tranca/{idTranca}/bicicleta")
    public ResponseEntity<Bicicleta> getBicicletaByTranca(@PathVariable Integer idTranca){
        Bicicleta bicicleta = service.getBicicletaByTranca(idTranca);

        if(bicicleta != null){
            return ResponseEntity.ok().body(bicicleta);
        }
        return null;
    }

    @PostMapping("/tranca/{idTranca}/trancar")
    public ResponseEntity<TrancaDto> statusTrancar(@PathVariable Integer idTranca, @RequestBody(required = false) Integer idBicicleta){
        Tranca tranca = service.trancar(idTranca, idBicicleta);
        return ResponseEntity.ok().body(converter.entityToDto(tranca));
    }

    @PostMapping("/tranca/{idTranca}/destrancar")
    public ResponseEntity<TrancaDto> statusDestrancar(@PathVariable Integer idTranca, @RequestBody(required = false) Integer idBicicleta){
        Tranca tranca = service.destrancar(idTranca, idBicicleta);
        return ResponseEntity.ok().body(converter.entityToDto(tranca));
    }

    @PostMapping("/tranca/{idTranca}/status/{acao}")
    public ResponseEntity<Tranca> postStatus(@PathVariable Integer idTranca, @PathVariable StatusTrancaEnum acao){
        Tranca trancaNovoStatus = service.postStatus(idTranca, acao);

        return ResponseEntity.ok().body(trancaNovoStatus);
    }
}
