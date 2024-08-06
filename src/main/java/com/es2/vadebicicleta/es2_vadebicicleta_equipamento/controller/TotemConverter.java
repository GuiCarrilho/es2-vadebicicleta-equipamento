package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TotemDto;
import org.springframework.stereotype.Component;

@Component
public class TotemConverter {

    public Totem dtoToEntity(TotemDto dto){
        Totem totem = new Totem();

        totem.setDescricao(dto.getDescricao());
        totem.setLocalizacao(dto.getLocalizacao());

        return totem;
    }
}
