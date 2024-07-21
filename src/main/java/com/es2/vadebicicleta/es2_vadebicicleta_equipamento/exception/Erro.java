package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Erro {
    private String codigo;
    private String mensagem;

}
