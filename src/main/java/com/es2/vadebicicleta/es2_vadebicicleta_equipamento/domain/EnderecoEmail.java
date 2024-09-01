package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEmail {

    private String assunto;
    private Long id;
    private String mensagem;
    private String email;
}