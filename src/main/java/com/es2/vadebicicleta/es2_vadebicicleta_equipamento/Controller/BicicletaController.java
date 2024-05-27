package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Service.BicicletaService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Domain.Bicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        Bicicleta novaBicicleta = service.cadastrarBicicleta(bicicleta);
        return ResponseEntity.ok(novaBicicleta);
    }

}


