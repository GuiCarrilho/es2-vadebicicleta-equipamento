package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String codigo;

    public NotFoundException(String codigo, String mensagem){
        super(mensagem);
        this.codigo = codigo;
    }
}
