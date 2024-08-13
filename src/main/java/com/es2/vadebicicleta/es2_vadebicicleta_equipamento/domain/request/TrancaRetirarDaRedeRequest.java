package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrancaRetirarDaRedeRequest {
    private Integer idTotem;
    private Integer idTranca;
    private Integer idFuncionario;
    private String statusAcaoReparador;

}
