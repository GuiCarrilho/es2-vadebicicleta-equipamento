package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IdGenerator {
    private Integer id;

    public Integer geradorId(){
        id++;
        return id;
    }
}
