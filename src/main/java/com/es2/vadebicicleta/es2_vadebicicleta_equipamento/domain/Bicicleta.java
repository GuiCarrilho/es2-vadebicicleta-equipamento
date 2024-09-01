package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bicicleta {

    private Integer id = null;
    private String marca;
    private String modelo;
    private String ano;
    private Integer numero;
    private String status;
    private LocalDateTime dataHoraInsRet;
    private Integer funcionario = 0;
}
