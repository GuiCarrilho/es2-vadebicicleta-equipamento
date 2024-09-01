package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import java.time.LocalDateTime;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tranca {

    private Integer id = null;
    private Integer bicicleta = 0;
    private Integer numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private String status;
    private LocalDateTime dataHoraInsRet;
    private Integer funcionario;
}
