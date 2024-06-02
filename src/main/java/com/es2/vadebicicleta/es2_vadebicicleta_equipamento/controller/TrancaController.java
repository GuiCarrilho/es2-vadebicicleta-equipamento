package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<Tranca> tranca = service.getById(idTranca);
        if(tranca.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(converter.entityToDto(tranca.get()));
    }

}
