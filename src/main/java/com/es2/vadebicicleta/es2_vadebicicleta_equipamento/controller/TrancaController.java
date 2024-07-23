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
    public ResponseEntity<List<Tranca>> getTrancas() {
        List<Tranca> trancas = service.getAll();
        if (trancas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(trancas);
    }

    @PostMapping("/tranca")
    public ResponseEntity<Tranca> postTranca(@RequestBody TrancaDto trancaDto) {
        Tranca tranca = converter.dtoToEntity(trancaDto);
        Tranca trancaNova = service.save(tranca);
        return ResponseEntity.ok().body(trancaNova);
    }

    @GetMapping("/tranca/{idTranca}")
    public ResponseEntity<Tranca> getTrancaById(@PathVariable Integer idTranca) {
        Tranca tranca = service.getById(idTranca);
        return ResponseEntity.ok().body(tranca);
    }

    @PutMapping("/tranca/{idTranca}")
    public ResponseEntity<Tranca> putTranca(@PathVariable Integer idTranca, @RequestBody TrancaDto trancaDto) {
        Tranca novaTranca = converter.dtoToEntity(trancaDto);
        Tranca trancaAtualizada = service.updateTranca(idTranca, novaTranca);
        return ResponseEntity.ok().body(trancaAtualizada);
    }

    @DeleteMapping("/tranca/{idTranca}")
    public ResponseEntity<Void> deleteTranca(@PathVariable Integer idTranca) {
        service.deleteTranca(idTranca);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tranca/{idTranca}/bicicleta")
    public ResponseEntity<Bicicleta> getBicicletaByTranca(@PathVariable Integer idTranca) {
        Bicicleta bicicleta = service.getBicicletaByTrancaId(idTranca);
        return ResponseEntity.ok().body(bicicleta);
    }

    @PostMapping("/tranca/{idTranca}/trancar")
    public ResponseEntity<Tranca> statusTrancar(@PathVariable Integer idTranca, @RequestBody(required = false) Integer idBicicleta) {
        Tranca tranca = service.trancar(idTranca, idBicicleta);
        return ResponseEntity.ok().body(tranca);
    }

    @PostMapping("/tranca/{idTranca}/destrancar")
    public ResponseEntity<Tranca> statusDestrancar(@PathVariable Integer idTranca, @RequestBody(required = false) Integer idBicicleta) {
        Tranca tranca = service.destrancar(idTranca, idBicicleta);
        return ResponseEntity.ok().body(tranca);
    }

    @PostMapping("/tranca/{idTranca}/status/{acao}")
    public ResponseEntity<Tranca> postStatus(@PathVariable Integer idTranca, @PathVariable StatusTrancaEnum acao) {
        Tranca trancaNovoStatus = service.postStatus(idTranca, acao);

        return ResponseEntity.ok().body(trancaNovoStatus);
    }

    @PostMapping("tranca/incluirNaRede")
    public ResponseEntity<Void> incluirNaRede(@RequestBody Integer idTotem, @RequestBody Integer idTranca, @RequestBody Integer idFuncionario) {
        service.incluirTrancaNaRedeTotem(idTotem, idTranca, idFuncionario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tranca/retirarDaRede")
    public ResponseEntity<Void> incluirDaRede(@RequestBody Integer idTotem, @RequestBody Integer idTranca, @RequestBody Integer idFuncionario, @RequestBody String statusAcaoReparador) {
        service.retirarTrancaDaRedeTotem(idTotem, idTranca, idFuncionario, statusAcaoReparador);
        return ResponseEntity.ok().build();
    }
}

