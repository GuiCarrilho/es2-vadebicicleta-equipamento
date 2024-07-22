package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bicicleta {

    private Integer id = null;
    @NotNull
    private String marca;
    @NotNull
    private String modelo;
    @NotNull
    private String ano;
    @NotNull
    private Integer numero;
    @NotNull
    private String status;
}
