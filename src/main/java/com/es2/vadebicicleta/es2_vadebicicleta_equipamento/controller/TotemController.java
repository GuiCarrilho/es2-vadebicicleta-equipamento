package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TotemController {

    private final TotemService service;

    @Autowired
    public TotemController(TotemService service) {
        this.service = service;
    }

    @GetMapping("/totem")
    public ResponseEntity<List<Totem>> getTotens(){
        List<Totem> totens = service.getAll();
        if(totens == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(totens);
    }

    @PostMapping("/totem")
    public ResponseEntity<Totem> postTotem(@RequestBody Totem totem){
        Totem novoTotem = service.save(totem);
        return ResponseEntity.ok(novoTotem);
    }
}
