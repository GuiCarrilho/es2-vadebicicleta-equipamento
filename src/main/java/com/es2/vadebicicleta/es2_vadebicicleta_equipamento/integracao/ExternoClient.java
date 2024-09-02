package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.EnderecoEmail;

@Component
public class ExternoClient {
    @Value("${vadebicicleta.externo.url}")
    private String url;

    private final RestTemplate template;

    @Autowired
    public ExternoClient(RestTemplate template) {
        this.template = template;
    }

    public EnderecoEmail enviarEmail(EnderecoEmail email) {
        ResponseEntity<EnderecoEmail> response = template.postForEntity(url+"/enviarEmail", email, EnderecoEmail.class);
        return response.getBody();

    }
}
