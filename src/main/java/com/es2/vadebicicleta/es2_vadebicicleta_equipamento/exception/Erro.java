package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Erro {
    private String codigo;
    private String mensagem;

    public Erro(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
