package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDto;
import org.springframework.stereotype.Component;

@Component
public class BicicletaConverter {

    public Bicicleta dtoToEntity(BicicletaDto dto){
        Bicicleta bicicleta = new Bicicleta();

        bicicleta.setModelo(dto.getModelo());
        bicicleta.setNumero(dto.getNumero());
        bicicleta.setMarca(dto.getMarca());
        bicicleta.setAno(dto.getAno());
        bicicleta.setStatus(dto.getStatus());

        return bicicleta;
    }
}
