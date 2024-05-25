package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Service;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.Domain.Bicicleta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BicicletaService {
    private List<Bicicleta> bicicletas = new ArrayList<>();

    public List<Bicicleta> getBicicletas() {
        return bicicletas;
    }

    public Bicicleta postBicicleta(Bicicleta bicicleta) {
        bicicletas.add(bicicleta);
        return bicicleta;

    }
}
