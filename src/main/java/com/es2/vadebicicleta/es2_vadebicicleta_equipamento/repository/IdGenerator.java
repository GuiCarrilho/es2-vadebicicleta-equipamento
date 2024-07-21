package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class IdGenerator {
    private Integer idBicicleta = 0;
    private Integer idTotem = 0;
    private Integer idTranca = 0;

    public Integer idBicicletaGenerator(){
        idBicicleta++;
        return idBicicleta;
    }

    public Integer idTotemGenerator(){
        idTotem++;
        return idTotem;
    }

    public Integer idTrancaGenerator(){
        idTranca++;
        return idTranca;
    }
}
