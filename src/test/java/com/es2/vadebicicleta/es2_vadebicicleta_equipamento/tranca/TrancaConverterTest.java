package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.tranca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TrancaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDtoReturn;

class TrancaConverterTest {

    private TrancaConverter trancaConverter;

    @BeforeEach
    void setUp() {
        trancaConverter = new TrancaConverter();
    }

    @Test
    void dtoToEntity_ValidDto_Success() {
        // Configura o DTO com dados válidos
        TrancaDto dto = new TrancaDto();
        dto.setLocalizacao("Localização A");
        dto.setAnoDeFabricacao("2023");
        dto.setNumero(456);
        dto.setModelo("Modelo Z");
        dto.setStatus("NOVA");

        // Converte o DTO para a entidade
        Tranca tranca = trancaConverter.dtoToEntity(dto);

        // Verifica se a conversão foi bem-sucedida
        assertNotNull(tranca);
        assertEquals("Localização A", tranca.getLocalizacao());
        assertEquals("2023", tranca.getAnoDeFabricacao());
        assertEquals(456, tranca.getNumero());
        assertEquals("Modelo Z", tranca.getModelo());
        assertEquals("NOVA", tranca.getStatus());
    }

    @Test
    void entityToDtoReturn_ValidEntity_Success() {
        // Configura a entidade com dados válidos
        Tranca tranca = new Tranca();
        tranca.setId(1);
        tranca.setBicicleta(1);
        tranca.setLocalizacao("Unirio");
        tranca.setAnoDeFabricacao("2019");
        tranca.setNumero(123);
        tranca.setModelo("Corrida");
        tranca.setStatus("NOVA");

        // Converte a entidade para o DTO
        TrancaDtoReturn dtoReturn = trancaConverter.entityToDtoReturn(tranca);

        // Verifica se a conversão foi bem-sucedida
        assertNotNull(dtoReturn);
        assertEquals(1, dtoReturn.getId());
        assertEquals(1, dtoReturn.getBicicleta());
        assertEquals("Unirio", dtoReturn.getLocalizacao());
        assertEquals("2019", dtoReturn.getAnoDeFabricacao());
        assertEquals(123, dtoReturn.getNumero());
        assertEquals("Corrida", dtoReturn.getModelo());
        assertEquals("NOVA", dtoReturn.getStatus());
    }
    
}
