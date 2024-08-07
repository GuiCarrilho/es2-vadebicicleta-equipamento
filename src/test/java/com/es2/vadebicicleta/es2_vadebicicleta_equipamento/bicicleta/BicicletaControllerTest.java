package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
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

    @Mock
    private BicicletaConverter converter;

    @InjectMocks
    private BicicletaController controller;

    private Bicicleta bicicleta;
    private BicicletaDto bicicletaDto;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "Disponível");
        bicicletaDto = new BicicletaDto("MarcaX", "Montanha", "2022", 123, "Disponível");
    }

    @Test
    void getBicicletas_Success() {
        // Mock do comportamento do serviço para retornar uma lista de totens
        when(service.getAll()).thenReturn(List.of(bicicleta));

        // Chama o método do controller e verifica o resultado
        ResponseEntity<List<Bicicleta>> response = controller.getBicicletas();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void postBicicleta_Success() {
        // Mock do comportamento do conversor e do serviço para salvar uma bicicleta
        when(converter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
        when(service.save(any(Bicicleta.class))).thenReturn(bicicleta);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Bicicleta> response = controller.postBicicleta(bicicletaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void postBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do conversor para retornar uma bicicleta inválida
        when(converter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
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
        // Mock do comportamento do conversor e do serviço para atualizar uma bicicleta
        when(converter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
        when(service.updateBicicleta(anyInt(), any(Bicicleta.class))).thenReturn(bicicleta);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Bicicleta> response = controller.putBicicleta(1, bicicletaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void putBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do conversor para retornar uma bicicleta inválida
        when(converter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
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
        // Mock do comportamento do conversor para retornar uma bicicleta inválida
        when(converter.dtoToEntity(any(BicicletaDto.class))).thenReturn(bicicleta);
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
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

    @Test
    void postStatus_Success() {
        // Mock do comportamento do serviço para atualizar o status da bicicleta
        when(service.postStatus(anyInt(), any(StatusBicicletaEnum.class))).thenReturn(bicicleta);

        // Chama o método do controller e verifica o resultado
        ResponseEntity<Bicicleta> response = controller.postStatus(1, StatusBicicletaEnum.DISPONIVEL);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bicicleta, response.getBody());
    }

    @Test
    void postStatus_BicicletaNotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.postStatus(anyInt(), any(StatusBicicletaEnum.class))).thenThrow(new NotFoundException("Bicicleta não encontrada"));

        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.postStatus(1, StatusBicicletaEnum.DISPONIVEL);
        });

        // Verifica a mensagem da exceção
        assertEquals("Bicicleta não encontrada", exception.getMessage());
    }

    @Test
    void postStatus_InvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.postStatus(anyInt(), any(StatusBicicletaEnum.class))).thenThrow(new InvalidActionException("Status não escolhido"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.postStatus(1, StatusBicicletaEnum.DISPONIVEL);
        });

        // Verifica a mensagem da exceção
        assertEquals("Status não escolhido", exception.getMessage());
    }

    @Test
    void incluirNaRede_Success() {
        // Mock do comportamento do serviço para incluir a bicicleta na rede
        doNothing().when(service).incluirBicicletaNaRedeTotem(anyInt(), anyInt(), anyInt());

        // Chama o método do controller e verifica o resultado
        ResponseEntity<Void> response = controller.incluirNaRede(1, 1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void incluirNaRede_InvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        doThrow(new InvalidActionException("Dados inválidos")).when(service).incluirBicicletaNaRedeTotem(anyInt(), anyInt(), anyInt());

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.incluirNaRede(1, 1, 1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados inválidos", exception.getMessage());
    }

    @Test
    void retirarDaRede_Success() {
        // Mock do comportamento do serviço para retirar a bicicleta da rede
        doNothing().when(service).retirarBicicletaDaRedeTotem(anyInt(), anyInt(), anyInt(), anyString());

        // Chama o método do controller e verifica o resultado
        ResponseEntity<Void> response = controller.retirarDaRede(1, 1, 1, "APOSENTADA");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void retirarDaRede_InvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        doThrow(new InvalidActionException("Dados inválidos")).when(service).retirarBicicletaDaRedeTotem(anyInt(), anyInt(), anyInt(), anyString());

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.retirarDaRede(1, 1, 1, "APOSENTADA");
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados inválidos", exception.getMessage());
    }
}