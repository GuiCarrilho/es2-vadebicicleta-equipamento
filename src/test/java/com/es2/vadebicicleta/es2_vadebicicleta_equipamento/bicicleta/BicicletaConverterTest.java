package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDto;

class BicicletaConverterTest {

    private BicicletaConverter bicicletaConverter;

    @BeforeEach
    void setUp() {
        bicicletaConverter = new BicicletaConverter();
    }

    @Test
    void dtoToEntity_ValidDto_Success() {
        // Configura o DTO com dados válidos
        BicicletaDto dto = new BicicletaDto();
        dto.setModelo("Montanha");
        dto.setNumero(123);
        dto.setMarca("Marca X");
        dto.setAno("2023");
        dto.setStatus("DISPONIVEL");

        // Converte o DTO para a entidade
        Bicicleta bicicleta = bicicletaConverter.dtoToEntity(dto);

        // Verifica se a conversão foi bem-sucedida
        assertNotNull(bicicleta);
        assertEquals("Montanha", bicicleta.getModelo());
        assertEquals(123, bicicleta.getNumero());
        assertEquals("Marca X", bicicleta.getMarca());
        assertEquals("2023", bicicleta.getAno());
        assertEquals("DISPONIVEL", bicicleta.getStatus());
    }

}
