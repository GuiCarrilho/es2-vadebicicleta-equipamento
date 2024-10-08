package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Aluguel;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Devolucao;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Funcionario;


@Component
public class AluguelClient {
    @Value("${vadebicicleta.aluguel.url}")
    private String url;

    private final RestTemplate template;

    @Autowired
    public AluguelClient(RestTemplate template){
        this.template = template;
    }

    public Funcionario obterFuncionario(Integer idFuncionario) {
        ResponseEntity<Funcionario> response = template.getForEntity(url+"/funcionario/{idFuncionario}", Funcionario.class, idFuncionario);
        if(!response.getStatusCode().equals(HttpStatus.OK)){
            return null;
        }
        return response.getBody();
    }



    public Aluguel devolucaoBicicleta(Devolucao devolucao){
        ResponseEntity<Aluguel> response = template.postForEntity(url+"/devolucao", devolucao ,Aluguel.class);
        if(!response.getStatusCode().equals(HttpStatus.OK)){
            return null;
        }
        return response.getBody();
    }

}
