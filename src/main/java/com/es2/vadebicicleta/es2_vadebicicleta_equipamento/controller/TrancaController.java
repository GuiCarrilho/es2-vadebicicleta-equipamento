package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(trancas);
    }

    @PostMapping("/tranca")
    public ResponseEntity<TrancaDto> postTranca(@RequestBody TrancaDto dto){
        Tranca tranca = converter.dtoToEntity(dto);
        Tranca trancaSalva = service.save(tranca);
        TrancaDto novoDto = converter.entityToDto(trancaSalva);

        return ResponseEntity.ok(novoDto);
    }


}
