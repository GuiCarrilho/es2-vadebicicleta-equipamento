package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bicicleta {

    private Integer id = null;
    private String marca;
    private String modelo;
    private String ano;
    private Integer numero;
    private String status;
}
