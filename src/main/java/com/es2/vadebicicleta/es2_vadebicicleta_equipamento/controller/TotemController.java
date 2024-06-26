package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/totem/{idTotem}")
    public ResponseEntity<Totem> putTotem(@PathVariable Integer idTotem, @RequestBody Totem novoTotem){
        Totem totemAtualizado = service.updateTotem(idTotem, novoTotem);
        if(totemAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(totemAtualizado);
    }

    @DeleteMapping("/totem/{idTotem}")
    public ResponseEntity<Totem> deleteTotem(@PathVariable Integer idTotem){
        Totem totemRemovido = service.deleteTotem(idTotem);
        if(totemRemovido == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/totem/{idTotem}/trancas")
    public ResponseEntity<List<Tranca>> getTrancasByTotemId(@PathVariable Integer idTotem){
        List<Tranca> trancas = service.getTrancasByTotem(idTotem);

        if(!trancas.isEmpty()){
            return ResponseEntity.ok().body(trancas);
        }
        return null;
    }

    @GetMapping("/totem/{idTotem}/bicicletas")
    public ResponseEntity<List<Bicicleta>> getBicicletasByTotem(@PathVariable Integer idTotem){
        List<Bicicleta> bicicletas = service.getBicicletasByTotem(idTotem);

        if(!bicicletas.isEmpty()){
            return ResponseEntity.ok().body(bicicletas);
        }
        return null;
    }
}
