package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception;

import lombok.Getter;

@Getter
public class InvalidActionException extends RuntimeException{
    private final String codigo;

    public InvalidActionException(String codigo, String mensagem){
        super(mensagem);
        this.codigo = codigo;
    }
}
