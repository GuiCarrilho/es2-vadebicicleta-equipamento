package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class BicicletaControllerTest {

    @Mock
    private BicicletaService service;

    @InjectMocks
    private BicicletaController controller;

    private BicicletaConverter converter;

    private Bicicleta bicicleta;
    private BicicletaDto bicicletaDto;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "Disponível");
        bicicletaDto = new BicicletaDto("MarcaX", "Montanha", "2022", 123, "Disponível");
        converter = new BicicletaConverter(); // Inicializa o conversor diretamente

        controller = new BicicletaController(service, converter);
    }

    @Test
    void getBicicletas_Success() {
        // Mock do comportamento do serviço para retornar uma lista de bicicletas
        when(service.getAll()).thenReturn(List.of(bicicleta));
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<List<Bicicleta>> response = controller.getBicicletas();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void postBicicleta_Success() {
        // Converte o DTO para entidade diretamente
        Bicicleta bicicletaNova = converter.dtoToEntity(bicicletaDto);
        // Mock do comportamento do serviço para salvar uma bicicleta
        when(service.save(any(Bicicleta.class))).thenReturn(bicicletaNova);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Bicicleta> response = controller.postBicicleta(bicicletaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bicicletaNova, response.getBody());
    }

    @Test
    void postBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.save(any(Bicicleta.class))).thenThrow(new InvalidActionException("Dados da bicicleta inválidos"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.postBicicleta(bicicletaDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados da bicicleta inválidos", exception.getMessage());
    }

    @Test
    void getBicicletaById_Success() {
        // Mock do comportamento do serviço para retornar uma bicicleta
        when(service.getById(anyInt())).thenReturn(bicicleta);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Bicicleta> response = controller.getBicicletaById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void getBicicletaById_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.getById(anyInt())).thenThrow(new NotFoundException("Bicicleta não existe"));
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getBicicletaById(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Bicicleta não existe", exception.getMessage());
    }

    @Test
    void putBicicleta_Success() {
        // Converte o DTO para entidade diretamente
        Bicicleta bicicletaAtualizada = converter.dtoToEntity(bicicletaDto);
        // Mock do comportamento do serviço para atualizar uma bicicleta
        when(service.updateBicicleta(anyInt(), any(Bicicleta.class))).thenReturn(bicicletaAtualizada);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Bicicleta> response = controller.putBicicleta(1, bicicletaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bicicletaAtualizada, response.getBody());
    }

    @Test
    void putBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.updateBicicleta(anyInt(), any(Bicicleta.class))).thenThrow(new InvalidActionException("Dados da bicicleta inválidos"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.putBicicleta(1, bicicletaDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados da bicicleta inválidos", exception.getMessage());
    }

    @Test
    void putBicicleta_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.updateBicicleta(anyInt(), any(Bicicleta.class))).thenThrow(new NotFoundException("Bicicleta não encontrada"));
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.putBicicleta(1, bicicletaDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Bicicleta não encontrada", exception.getMessage());
    }

    @Test
    void deleteBicicleta_Success() {
        // Mock do comportamento do serviço para excluir uma bicicleta
        doNothing().when(service).deleteBicicleta(anyInt());
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Void> response = controller.deleteBicicleta(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteBicicleta_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        doThrow(new NotFoundException("Bicicleta não encontrada")).when(service).deleteBicicleta(anyInt());
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.deleteBicicleta(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Bicicleta não encontrada", exception.getMessage());
    }
}


