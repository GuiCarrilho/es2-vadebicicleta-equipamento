package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDtoReturn;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.BicicletaIncluirNaRedeRequest;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.BicicletaRetirarDaRedeRequest;
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
    public ResponseEntity<BicicletaDtoReturn> postBicicleta(@RequestBody BicicletaDto bicicletaDto) {
        Bicicleta bicicleta = converter.dtoToEntity(bicicletaDto);
        Bicicleta novaBicicleta = service.save(bicicleta);
        BicicletaDtoReturn dtoReturn = converter.entityToDtoReturn(novaBicicleta);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @GetMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<BicicletaDtoReturn> getBicicletaById(@PathVariable Integer idBicicleta) {
        Bicicleta bicicleta = service.getById(idBicicleta);
        BicicletaDtoReturn dtoReturn = converter.entityToDtoReturn(bicicleta);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @PutMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<BicicletaDtoReturn> putBicicleta(@PathVariable Integer idBicicleta, @RequestBody BicicletaDto bicicletaDto) {
        Bicicleta novaBicicleta = converter.dtoToEntity(bicicletaDto);
        Bicicleta bicicletaAtualizada = service.updateBicicleta(idBicicleta, novaBicicleta);
        BicicletaDtoReturn dtoReturn = converter.entityToDtoReturn(bicicletaAtualizada);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @DeleteMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Void> deleteBicicleta(@PathVariable Integer idBicicleta) {
        service.deleteBicicleta(idBicicleta);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bicicleta/{idBicicleta}/status/{acao}")
    public ResponseEntity<BicicletaDtoReturn> postStatus(@PathVariable Integer idBicicleta, @PathVariable StatusBicicletaEnum acao) {
        Bicicleta bicicletaNovoStatus = service.postStatus(idBicicleta, acao);
        BicicletaDtoReturn dtoReturn = converter.entityToDtoReturn(bicicletaNovoStatus);
        return ResponseEntity.ok().body(dtoReturn);
    }

    @PostMapping("bicicleta/incluirNaRede")
    public ResponseEntity<Void> incluirNaRede(@RequestBody BicicletaIncluirNaRedeRequest request){
        Integer idTranca = request.getIdTranca();
        Integer idBicicleta = request.getIdBicicleta();
        Integer idFuncionario = request.getIdFuncionario();
        service.incluirBicicletaNaRedeTotem(idTranca, idBicicleta, idFuncionario);
        return ResponseEntity.ok().build();
}

    @PostMapping("/bicicleta/retirarDaRede")
    public ResponseEntity<Void> retirarDaRede(@RequestBody BicicletaRetirarDaRedeRequest request ){
        Integer idTranca = request.getIdTranca();
        Integer idBicicleta = request.getIdBicicleta();
        Integer idFuncionario = request.getIdFuncionario();
        String statusAcaoReparador = request.getStatusAcaoReparador();
        service.retirarBicicletaDaRedeTotem(idTranca, idBicicleta, idFuncionario, statusAcaoReparador);
        return ResponseEntity.ok().build();
    }
}

