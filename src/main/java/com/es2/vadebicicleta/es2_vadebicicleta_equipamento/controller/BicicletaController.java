package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.springframework.http.ResponseEntity;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok(novaBicicleta);
    }

    @GetMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Object> getBicicletaById(@PathVariable Integer idBicicleta) {
        Optional<Bicicleta> bicicleta = service.getById(idBicicleta);
        if (bicicleta.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bicicleta);
    }

    @PutMapping("/bicicleta/{idBicicleta}")
    public ResponseEntity<Bicicleta> putBicicleta(@PathVariable Integer idBicicleta, @RequestBody Bicicleta novaBicicleta) {
        Bicicleta bicicletaAtualizada = service.updateBicicleta(idBicicleta, novaBicicleta);
        if(bicicletaAtualizada != null) {
            return ResponseEntity.ok(bicicletaAtualizada);
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
}


