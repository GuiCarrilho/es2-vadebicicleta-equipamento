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
public class Totem {

    private Integer id = null;
    private String localizacao;
    private String descricao;
}
