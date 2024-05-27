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

    @Autowired
    private BicicletaService service;

    @GetMapping("/bicicleta")
    public ResponseEntity<List<Bicicleta>> getBicicletas() {
        List<Bicicleta> bicicletas = service.listarBicicletas();
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
}


