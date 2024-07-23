package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tranca {

    private Integer id = null;
    private Integer bicicleta = 0;
    private Integer numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private String status;
}
