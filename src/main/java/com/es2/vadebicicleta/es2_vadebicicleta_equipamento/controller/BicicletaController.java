package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.Erro;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.springframework.http.ResponseEntity;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BicicletaController {

    private final BicicletaService service;

    @Autowired
    public BicicletaController(BicicletaService service) {
        this.service = service;
    }

    @GetMapping("/bicicleta")
    public ResponseEntity<List<Bicicleta>> getBicicletas() {
        List<Bicicleta> bicicletas = service.getAll();
        if(bicicletas == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bicicletas);
    }

    @PostMapping("/bicicleta")
    public ResponseEntity<Object> postBicicleta(@RequestBody Bicicleta bicicleta) {
        try {
            Bicicleta novaBicicleta = service.save(bicicleta);
            return ResponseEntity.ok().body(novaBicicleta);
        } catch (InvalidActionException e){
            return ResponseEntity.status(422).body(new Erro("422", e.getMessage()));
        }
    }

    @GetMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Object> getBicicletaById(@PathVariable Integer idBicicleta) {
        try {
            Bicicleta bicicleta = service.getById(idBicicleta);
            return ResponseEntity.ok().body(bicicleta);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(new Erro("404", e.getMessage()));
        }
    }

    @PutMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Object> putBicicleta(@PathVariable Integer idBicicleta, @RequestBody Bicicleta novaBicicleta) {
        try {
            Bicicleta bicicletaAtualizada = service.updateBicicleta(idBicicleta, novaBicicleta);
            return ResponseEntity.ok().body(bicicletaAtualizada);
        }catch (NotFoundException e){
            return ResponseEntity.status(404).body(new Erro("404", e.getMessage()));
        }catch (InvalidActionException e){
            return ResponseEntity.status(422).body(new Erro("422", e.getMessage()));
        }
    }

    @DeleteMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Object> deleteBicicleta(@PathVariable Integer idBicicleta) {
        try {
            service.deleteBicicleta(idBicicleta);
            return ResponseEntity.notFound().build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new Erro("404", e.getMessage()));
        }
    }

    @PostMapping("/bicicleta/{idBicicleta}/status/{acao}")
    public ResponseEntity<Bicicleta> postStatus(@PathVariable Integer idBicicleta, @PathVariable StatusBicicletaEnum acao){
        Bicicleta bicicletaNovoStatus = service.postStatus(idBicicleta, acao);

        return ResponseEntity.ok().body(bicicletaNovoStatus);
    }
}


