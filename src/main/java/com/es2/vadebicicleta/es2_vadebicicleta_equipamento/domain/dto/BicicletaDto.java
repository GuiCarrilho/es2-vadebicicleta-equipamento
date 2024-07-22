package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BicicletaDto {

    private String marca;
    private String modelo;
    private String ano;
    private Integer numero;
    private String status;
}
