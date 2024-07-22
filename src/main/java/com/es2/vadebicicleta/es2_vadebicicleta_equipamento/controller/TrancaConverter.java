package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TrancaConverter {

    private final BicicletaService service;

    @Autowired
    public TrancaConverter(BicicletaService service) {
        this.service = service;
    }

    public TrancaDto entityToDto(Tranca tranca){
        TrancaDto dto = new TrancaDto();

        dto.setNumero(tranca.getNumero());
        dto.setAnoDeFabricacao(tranca.getAnoDeFabricacao());
        dto.setLocalizacao(tranca.getLocalizacao());
        dto.setModelo(tranca.getModelo());
        dto.setStatus(tranca.getStatus());

        return dto;
    }

    public Tranca dtoToEntity(TrancaDto dto){
        Tranca tranca = new Tranca();

        tranca.setLocalizacao(dto.getLocalizacao());
        tranca.setAnoDeFabricacao(dto.getAnoDeFabricacao());
        tranca.setNumero(dto.getNumero());
        tranca.setModelo(dto.getModelo());
        tranca.setStatus(dto.getStatus());

        return tranca;
    }
}
