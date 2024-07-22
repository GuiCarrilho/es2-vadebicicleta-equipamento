package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TotemDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.Erro;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TotemController {

    private final TotemService service;
    private final TotemConverter converter;

    @Autowired
    public TotemController(TotemService service, TotemConverter converter) {

        this.service = service;
        this.converter = converter;
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
    public ResponseEntity<Totem> postTotem(@RequestBody TotemDto totemDto){
        Totem totem = converter.dtoToEntity(totemDto);
        Totem novoTotem = service.save(totem);
        return ResponseEntity.ok().body(novoTotem);
    }

    @PutMapping("/totem/{idTotem}")
    public ResponseEntity<Totem> putTotem(@PathVariable Integer idTotem, @RequestBody TotemDto totemDto){
        Totem novoTotem = converter.dtoToEntity(totemDto);
        Totem totemAtualizado = service.updateTotem(idTotem, novoTotem);
        return ResponseEntity.ok().body(totemAtualizado);
    }

    @DeleteMapping("/totem/{idTotem}")
    public ResponseEntity<Void> deleteTotem(@PathVariable Integer idTotem){
        service.deleteTotem(idTotem);
        return ResponseEntity.notFound().build();
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
