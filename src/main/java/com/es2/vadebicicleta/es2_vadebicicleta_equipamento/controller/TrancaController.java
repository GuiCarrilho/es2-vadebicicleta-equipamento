package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
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
    public ResponseEntity<TrancaDto> postTranca(@RequestBody TrancaDto dto){
        Tranca tranca = converter.dtoToEntity(dto);
        Tranca trancaSalva = service.save(tranca);

        return ResponseEntity.ok().body(converter.entityToDto(trancaSalva));
    }

    @GetMapping("/tranca/{idTranca}")
    public ResponseEntity<TrancaDto> getTrancaById(@PathVariable Integer idTranca){
        Tranca tranca = service.getById(idTranca);
        return ResponseEntity.ok().body(converter.entityToDto(tranca));
    }

    @PutMapping("/tranca/{idTranca}")
    public ResponseEntity<TrancaDto> putTranca(@PathVariable Integer idTranca, @RequestBody TrancaDto novaTranca){
        Tranca trancaAtualizada = service.updateTranca(idTranca, novaTranca);
        return ResponseEntity.ok().body(converter.entityToDto(trancaAtualizada));
    }

    @DeleteMapping("/tranca/{idTranca}")
    public ResponseEntity<Tranca> deleteTranca(@PathVariable Integer idTranca){
        Tranca trancaRemovida = service.deleteTranca(idTranca);
        if(trancaRemovida == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
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

    @PostMapping("/tranca/{idTranca}/status/{acao}")
    public ResponseEntity<Tranca> postStatus(@PathVariable Integer idTranca, @PathVariable StatusTrancaEnum acao){
        Tranca trancaNovoStatus = service.postStatus(idTranca, acao);

        return ResponseEntity.ok().body(trancaNovoStatus);
    }
}
