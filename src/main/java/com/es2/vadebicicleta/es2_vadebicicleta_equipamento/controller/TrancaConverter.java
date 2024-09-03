package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDtoReturn;

import org.springframework.stereotype.Component;

@Component
public class TrancaConverter {

    public Tranca dtoToEntity(TrancaDto dto){
        Tranca tranca = new Tranca();

        tranca.setLocalizacao(dto.getLocalizacao());
        tranca.setAnoDeFabricacao(dto.getAnoDeFabricacao());
        tranca.setNumero(dto.getNumero());
        tranca.setModelo(dto.getModelo());
        tranca.setStatus(dto.getStatus());

        return tranca;
    }

    public TrancaDtoReturn entityToDtoReturn(Tranca tranca){
        TrancaDtoReturn dtoReturn = new TrancaDtoReturn();

        dtoReturn.setId(tranca.getId());
        dtoReturn.setBicicleta(tranca.getBicicleta());
        dtoReturn.setLocalizacao(tranca.getLocalizacao());
        dtoReturn.setAnoDeFabricacao(tranca.getAnoDeFabricacao());
        dtoReturn.setNumero(tranca.getNumero());
        dtoReturn.setModelo(tranca.getModelo());
        dtoReturn.setStatus(tranca.getStatus());

        return dtoReturn;
    }
}
