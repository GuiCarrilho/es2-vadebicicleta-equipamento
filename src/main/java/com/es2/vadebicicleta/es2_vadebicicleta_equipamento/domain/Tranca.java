package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tranca {

    private Integer id;
    private Integer bicicleta;
    private Integer numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private String status;
}
