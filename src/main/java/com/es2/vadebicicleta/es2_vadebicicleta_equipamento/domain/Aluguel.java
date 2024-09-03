package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aluguel {

    private Integer bicicleta;
    private String horaInicio;
    private Integer trancaFim;
    private String horaFim;
    private Long cobranca;
    private Integer ciclista;

}
