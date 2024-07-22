package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrancaDto {

    private Integer numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private String status;
}
