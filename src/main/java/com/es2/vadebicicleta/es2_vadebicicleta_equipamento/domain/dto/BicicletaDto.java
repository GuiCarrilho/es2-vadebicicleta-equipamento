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
