package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.springframework.http.ResponseEntity;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BicicletaController {

    private final BicicletaService service;
    private final BicicletaConverter converter;

    @Autowired
    public BicicletaController(BicicletaService service, BicicletaConverter converter) {

        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/bicicleta")
    public ResponseEntity<List<Bicicleta>> getBicicletas() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @PostMapping("/bicicleta")
    public ResponseEntity<Bicicleta> postBicicleta(@RequestBody BicicletaDto bicicletaDto) {
        Bicicleta bicicleta = converter.dtoToEntity(bicicletaDto);
        Bicicleta novaBicicleta = service.save(bicicleta);
        return ResponseEntity.ok().body(novaBicicleta);
    }

    @GetMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Bicicleta> getBicicletaById(@PathVariable Integer idBicicleta) {
        Bicicleta bicicleta = service.getById(idBicicleta);
        return ResponseEntity.ok().body(bicicleta);
    }

    @PutMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Bicicleta> putBicicleta(@PathVariable Integer idBicicleta, @RequestBody BicicletaDto bicicletaDto) {
        Bicicleta novaBicicleta = converter.dtoToEntity(bicicletaDto);
        Bicicleta bicicletaAtualizada = service.updateBicicleta(idBicicleta, novaBicicleta);
        return ResponseEntity.ok().body(bicicletaAtualizada);
    }

    @DeleteMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Void> deleteBicicleta(@PathVariable Integer idBicicleta) {
        service.deleteBicicleta(idBicicleta);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bicicleta/{idBicicleta}/status/{acao}")
    public ResponseEntity<Bicicleta> postStatus(@PathVariable Integer idBicicleta, @PathVariable StatusBicicletaEnum acao) {
        Bicicleta bicicletaNovoStatus = service.postStatus(idBicicleta, acao);
        return ResponseEntity.ok().body(bicicletaNovoStatus);
    }

    @PostMapping("bicicleta/incluirNaRede")
    public ResponseEntity<Void> incluirNaRede(@RequestBody Integer idTranca, @RequestBody Integer idBicicleta, @RequestBody Integer idFuncionario){
        service.incluirBicicletaNaRedeTotem(idTranca, idBicicleta, idFuncionario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bicicleta/retirarDaRede")
    public ResponseEntity<Void> retirarDaRede(@RequestBody Integer idTranca, @RequestBody Integer idBicicleta, @RequestBody Integer idFuncionario, @RequestBody String statusAcaoReparador){
        service.retirarBicicletaDaRedeTotem(idTranca, idBicicleta, idFuncionario, statusAcaoReparador);
        return ResponseEntity.ok().build();
    }
}

