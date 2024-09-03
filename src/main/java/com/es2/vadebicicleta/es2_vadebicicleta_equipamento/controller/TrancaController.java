package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDtoReturn;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDtoReturn;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.TrancaIncluirNaRedeRequest;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.TrancaRequest;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.TrancaRetirarDaRedeRequest;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrancaController {

    private final TrancaService service;

    private final TrancaConverter converter;

    private final BicicletaConverter bicicletaConverter;

    @Autowired
    public TrancaController(TrancaService service, TrancaConverter converter, BicicletaConverter bicicletaConverter) {
        this.service = service;
        this.converter = converter;
        this.bicicletaConverter = bicicletaConverter;
    }

    @GetMapping("/tranca")
    public ResponseEntity<List<Tranca>> getTrancas() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @PostMapping("/tranca")
    public ResponseEntity<TrancaDtoReturn> postTranca(@RequestBody TrancaDto trancaDto) {
        Tranca tranca = converter.dtoToEntity(trancaDto);
        Tranca trancaNova = service.save(tranca);
        TrancaDtoReturn dtoReturn = converter.entityToDtoReturn(trancaNova);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @GetMapping("/tranca/{idTranca}")
    public ResponseEntity<TrancaDtoReturn> getTrancaById(@PathVariable Integer idTranca) {
        Tranca tranca = service.getById(idTranca);
        TrancaDtoReturn dtoReturn = converter.entityToDtoReturn(tranca);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @PutMapping("/tranca/{idTranca}")
    public ResponseEntity<TrancaDtoReturn> putTranca(@PathVariable Integer idTranca, @RequestBody TrancaDto trancaDto) {
        Tranca novaTranca = converter.dtoToEntity(trancaDto);
        Tranca trancaAtualizada = service.updateTranca(idTranca, novaTranca);
        TrancaDtoReturn dtoReturn = converter.entityToDtoReturn(trancaAtualizada);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @DeleteMapping("/tranca/{idTranca}")
    public ResponseEntity<Void> deleteTranca(@PathVariable Integer idTranca) {
        service.deleteTranca(idTranca);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tranca/{idTranca}/trancar")
    public ResponseEntity<TrancaDtoReturn> statusTrancar(@PathVariable Integer idTranca, @RequestBody(required = false) TrancaRequest request) {
        Integer idBicicleta = (request != null) ? request.getIdBicicleta() : null;
        Tranca tranca = service.trancar(idTranca, idBicicleta);
        TrancaDtoReturn dtoReturn = converter.entityToDtoReturn(tranca);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @PostMapping("/tranca/{idTranca}/destrancar")
    public ResponseEntity<TrancaDtoReturn> statusDestrancar(@PathVariable Integer idTranca, @RequestBody(required = false) TrancaRequest request) {
        Integer idBicicleta = (request != null) ? request.getIdBicicleta() : null;
        Tranca tranca = service.destrancar(idTranca, idBicicleta);
        TrancaDtoReturn dtoReturn = converter.entityToDtoReturn(tranca);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @PostMapping("/tranca/{idTranca}/status/{acao}")
    public ResponseEntity<TrancaDtoReturn> postStatus(@PathVariable Integer idTranca, @PathVariable StatusTrancaEnum acao) {
        Tranca trancaNovoStatus = service.postStatus(idTranca, acao);
        TrancaDtoReturn dtoReturn = converter.entityToDtoReturn(trancaNovoStatus);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @PostMapping("/tranca/integrarNaRede")
    public ResponseEntity<Void> incluirNaRede(@RequestBody TrancaIncluirNaRedeRequest request) {
    Integer idTotem = request.getIdTotem();
    Integer idTranca = request.getIdTranca();
    Integer idFuncionario = request.getIdFuncionario();
    service.incluirTrancaNaRedeTotem(idTotem, idTranca, idFuncionario);
    return ResponseEntity.ok().build();
    }
    
    @PostMapping("/tranca/retirarDaRede")
    public ResponseEntity<Void> retirarDaRede(@RequestBody TrancaRetirarDaRedeRequest request) {
    Integer idTotem = request.getIdTotem();
    Integer idTranca = request.getIdTranca();
    Integer idFuncionario = request.getIdFuncionario();
    String statusAcaoReparador = request.getStatusAcaoReparador();
    service.retirarTrancaDaRedeTotem(idTotem, idTranca, idFuncionario, statusAcaoReparador);
    return ResponseEntity.ok().build();
}


    @GetMapping("/tranca/{idTranca}/bicicleta")
    public ResponseEntity<BicicletaDtoReturn> getBicicletaByTrancaId(@PathVariable Integer idTranca){
        Bicicleta bicicleta = service.getBicicletaByTrancaId(idTranca);
        BicicletaDtoReturn dtoReturn = bicicletaConverter.entityToDtoReturn(bicicleta);
        return ResponseEntity.ok().body(dtoReturn);
    }

}

