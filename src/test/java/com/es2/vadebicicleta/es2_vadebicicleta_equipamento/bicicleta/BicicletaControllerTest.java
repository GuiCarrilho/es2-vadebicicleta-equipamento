package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class BicicletaControllerTest {

    @Mock
    private BicicletaService bicicletaService;

    @Mock
    private BicicletaConverter bicicletaConverter;

    @InjectMocks
    private BicicletaController bicicletaController;

    private Bicicleta bicicleta;
    private BicicletaDto bicicletaDto;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta e um DTO para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "Caloi", "Mountain Bike", "2022", 123, "Disponível");
        bicicletaDto = new BicicletaDto("Caloi", "Mountain Bike", "2022", 123, "Disponível");
    }

    @Test
    void getBicicletas_Success() {
        // Mock do comportamento do método getAll do serviço
        when(bicicletaService.getAll()).thenReturn(Arrays.asList(bicicleta));
        
        // Chama o endpoint para obter todas as bicicletas
        ResponseEntity<List<Bicicleta>> response = bicicletaController.getBicicletas();
        
        // Verifica se o status é 200 OK e o corpo contém a bicicleta
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(bicicleta));
    }

    @Test
    void getBicicletas_NoContent() {
        // Mock do comportamento do método getAll do serviço
        when(bicicletaService.getAll()).thenReturn(null);
        
        // Chama o endpoint para obter todas as bicicletas
        ResponseEntity<List<Bicicleta>> response = bicicletaController.getBicicletas();
        
        // Verifica se o status é 404 Not Found
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void postBicicleta_Success() {
        // Mock do comportamento do método dtoToEntity do conversor
        when(bicicletaConverter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
        // Mock do comportamento do método save do serviço
        when(bicicletaService.save(any(Bicicleta.class))).thenReturn(bicicleta);
        
        // Chama o endpoint para criar uma nova bicicleta
        ResponseEntity<Bicicleta> response = bicicletaController.postBicicleta(bicicletaDto);
        
        // Verifica se o status é 200 OK e o corpo contém a bicicleta criada
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void getBicicletaById_Success() {
        // Mock do comportamento do método getById do serviço
        when(bicicletaService.getById(anyInt())).thenReturn(bicicleta);
        
        // Chama o endpoint para obter uma bicicleta pelo ID
        ResponseEntity<Bicicleta> response = bicicletaController.getBicicletaById(1);
        
        // Verifica se o status é 200 OK e o corpo contém a bicicleta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void getBicicletaById_NotFound() {
        // Mock do comportamento do método getById do serviço
        when(bicicletaService.getById(anyInt())).thenThrow(NotFoundException.class);
        
        // Chama o endpoint para obter uma bicicleta pelo ID
        ResponseEntity<Bicicleta> response = bicicletaController.getBicicletaById(1);
        
        // Verifica se o status é 404 Not Found
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void putBicicleta_Success() {
        // Mock do comportamento do método dtoToEntity do conversor
        when(bicicletaConverter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
        // Mock do comportamento do método updateBicicleta do serviço
        when(bicicletaService.updateBicicleta(anyInt(), any(Bicicleta.class))).thenReturn(bicicleta);
        
        // Chama o endpoint para atualizar uma bicicleta
        ResponseEntity<Bicicleta> response = bicicletaController.putBicicleta(1, bicicletaDto);
        
        // Verifica se o status é 200 OK e o corpo contém a bicicleta atualizada
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void deleteBicicleta_Success() {
        // Mock do comportamento do método deleteBicicleta do serviço
        doNothing().when(bicicletaService).deleteBicicleta(anyInt());
        
        // Chama o endpoint para excluir uma bicicleta
        ResponseEntity<Void> response = bicicletaController.deleteBicicleta(1);
        
        // Verifica se o status é 200 OK
        assertEquals(200, response.getStatusCodeValue());
    }
}
