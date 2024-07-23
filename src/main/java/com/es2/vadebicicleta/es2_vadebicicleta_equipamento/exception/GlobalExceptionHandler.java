package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Erro> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String mensagem = "Ação inválida: " + ex.getValue();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Erro("422", mensagem));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Erro> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String mensagem = "Invalid request parameters: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Erro("422", mensagem));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Erro> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro("404", ex.getMessage()));
    }

    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<Erro> handleInavlidActionException(InvalidActionException ex){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Erro("422", ex.getMessage()));
    }

}
