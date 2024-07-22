package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
