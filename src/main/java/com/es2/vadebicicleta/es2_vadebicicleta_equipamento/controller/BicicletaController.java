package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusEnum;
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
    public ResponseEntity<Bicicleta> postBicicleta(@RequestBody Bicicleta bicicleta) {
        Bicicleta novaBicicleta = service.save(bicicleta);
        return ResponseEntity.ok().body(novaBicicleta);
    }

    @GetMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Object> getBicicletaById(@PathVariable Integer idBicicleta) {
        Bicicleta bicicleta = service.getById(idBicicleta);
        return ResponseEntity.ok().body(bicicleta);
    }

    @PutMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Bicicleta> putBicicleta(@PathVariable Integer idBicicleta, @RequestBody Bicicleta novaBicicleta) {
        Bicicleta bicicletaAtualizada = service.updateBicicleta(idBicicleta, novaBicicleta);
        if(bicicletaAtualizada != null) {
            return ResponseEntity.ok().body(bicicletaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Bicicleta> deleteBicicleta(@PathVariable Integer idBicicleta) {
        Bicicleta bicicletaRemovida = service.deleteBicicleta(idBicicleta);
        if(bicicletaRemovida == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bicicleta/{idBicicleta}/status/{acao}")
    public ResponseEntity<Bicicleta> postStatus(@PathVariable Integer idBicicleta, @PathVariable StatusEnum acao){
        Bicicleta bicicletaNovoStatus = service.postStatus(idBicicleta, acao);

        return ResponseEntity.ok().body(bicicletaNovoStatus);
    }
}


