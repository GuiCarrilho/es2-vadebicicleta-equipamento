package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidActionException extends RuntimeException{
    public InvalidActionException(String mensagem){
        super(mensagem);
    }
}
