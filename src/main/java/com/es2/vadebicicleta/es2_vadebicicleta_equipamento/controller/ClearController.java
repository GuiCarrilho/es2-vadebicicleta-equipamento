package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.IdGenerator;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;

@RestController
public class ClearController {

    private final BicicletaService bicicletaService;
    private final TotemService totemService;
    private final TrancaService trancaService;
    private final IdGenerator idGenerator;

    @Autowired
    public ClearController(BicicletaService bicicletaService, TotemService totemService, TrancaService trancaService, IdGenerator idGenerator){
        this.bicicletaService = bicicletaService;
        this.totemService = totemService;
        this.trancaService = trancaService;
        this.idGenerator = idGenerator;
    }

    @GetMapping("/restaurarDados")
    public ResponseEntity<Void> restaurarDados(){
        bicicletaService.bicicletaClear();
        totemService.totemClear();
        trancaService.trancaClear();
        idGenerator.idGeneratorClear();
        bicicletaService.initialData();
        totemService.initialData();
        trancaService.initialData();
        return ResponseEntity.ok().build();
    }
}
