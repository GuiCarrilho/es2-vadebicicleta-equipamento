package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrancaIncluirNaRedeRequest {
    private Integer idTotem;
    private Integer idTranca;
    private Integer idFuncionario;

}
