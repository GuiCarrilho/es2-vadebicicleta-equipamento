package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Service.BicicletaService;
import org.springframework.stereotype.Controller;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Domain.Bicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping
public class BicicletaController {
    private final BicicletaService bicicletaService;

    @Autowired
    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping("/bicicleta")
    public List<Bicicleta> getAllBicicletas() {
        return bicicletaService.getBicicletas();
    }

    @PostMapping("/bicicleta")
    public Bicicleta cadastrarBicicleta(@RequestBody Bicicleta bicicleta) {
        return bicicletaService.postBicicleta(bicicleta);
    }
}


