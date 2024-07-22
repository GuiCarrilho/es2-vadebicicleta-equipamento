package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrancaDto {

    @NotNull
    private Integer numero;
    @NotNull
    private String localizacao;
    @NotNull
    private String anoDeFabricacao;
    @NotNull
    private String modelo;
    @NotNull
    private String status;
}
