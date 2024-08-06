package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
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
}
