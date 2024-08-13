package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.totem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TotemConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TotemDto;

class TotemConverterTest {
    
    private TotemConverter totemConverter;

    @BeforeEach
    void setUp() {
        totemConverter = new TotemConverter();
    }

    @Test
    void dtoToEntity_ValidDto_Success() {
        // Configura o DTO com dados válidos
        TotemDto dto = new TotemDto();
        dto.setDescricao("Descrição do Totem");
        dto.setLocalizacao("Localização A");

        // Converte o DTO para a entidade
        Totem totem = totemConverter.dtoToEntity(dto);

        // Verifica se a conversão foi bem-sucedida
        assertNotNull(totem);
        assertEquals("Descrição do Totem", totem.getDescricao());
        assertEquals("Localização A", totem.getLocalizacao());
    }

}
