package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.tranca;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TotemConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TrancaController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TrancaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TotemDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;
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
class TrancaControllerTest {

    @Mock
    private TrancaService service;

    @InjectMocks
    private TrancaController controller;

    private TrancaConverter converter;

    private Tranca tranca;
    private TrancaDto trancaDto;

    @BeforeEach
    void setUp() {
        // Configura um objeto Tranca para ser usado em todos os testes
        tranca = new Tranca(1, 0, 123, "Unirio", "2019", "Corrida", "Trancar");
        trancaDto = new TrancaDto(123, "Unirio", "2019", "Corrida", "Trancar");
        converter = new TrancaConverter(); // Inicializa o conversor diretamente

        controller = new TrancaController(service, converter);
    }

    @Test
    void getTrancas_Success() {
        // Mock do comportamento do serviço para retornar uma lista de trancass
        when(service.getAll()).thenReturn(List.of(tranca));
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<List<Tranca>> response = controller.getTrancas();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void postTranca_Success() {
        // Converte o DTO para entidade diretamente
        Tranca trancaNova = converter.dtoToEntity(trancaDto);
        // Mock do comportamento do serviço para salvar uma tranca
        when(service.save(any(Tranca.class))).thenReturn(trancaNova);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Tranca> response = controller.postTranca(trancaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaNova, response.getBody());
    }

    @Test
    void postTranca_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.save(any(Tranca.class))).thenThrow(new InvalidActionException("Dados da tranca inválidos"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.postTranca(trancaDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados da tranca inválidos", exception.getMessage());
    }

    @Test
    void getTrancaById_Success() {
        // Mock do comportamento do serviço para retornar uma tranca
        when(service.getById(anyInt())).thenReturn(tranca);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Tranca> response = controller.getTrancaById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tranca, response.getBody());
    }

    @Test
    void getTrancaById_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.getById(anyInt())).thenThrow(new NotFoundException("Tranca não existe"));
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getTrancaById(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Tranca não existe", exception.getMessage());
    }

    @Test
    void putTranca_Success() {
        // Converte o DTO para entidade diretamente
        Tranca trancaAtualizada = converter.dtoToEntity(trancaDto);
        // Mock do comportamento do serviço para atualizar uma tranca
        when(service.updateTranca(anyInt(), any(Tranca.class))).thenReturn(trancaAtualizada);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Tranca> response = controller.putTranca(1, trancaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaAtualizada, response.getBody());
    }

    @Test
    void putTranca_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.updateTranca(anyInt(), any(Tranca.class))).thenThrow(new InvalidActionException("Dados da tranca inválidos"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.putTranca(1, trancaDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados da tranca inválidos", exception.getMessage());
    }

    @Test
    void putTranca_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.updateTranca(anyInt(), any(Tranca.class))).thenThrow(new NotFoundException("Tranca não encontrada"));
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.putTranca(1, trancaDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Tranca não encontrada", exception.getMessage());
    }

    @Test
    void deleteTranca_Success() {
        // Mock do comportamento do serviço para excluir uma tranca
        doNothing().when(service).deleteTranca(anyInt());
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Void> response = controller.deleteTranca(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTranca_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        doThrow(new NotFoundException("Tranca não encontrada")).when(service).deleteTranca(anyInt());
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.deleteTranca(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Tranca não encontrada", exception.getMessage());
    }
}
