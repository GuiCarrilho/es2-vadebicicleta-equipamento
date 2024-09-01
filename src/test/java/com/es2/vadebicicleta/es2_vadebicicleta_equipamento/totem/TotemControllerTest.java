package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.totem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TotemController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TotemConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TotemDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
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
class TotemControllerTest {

    @Mock
    private TotemService service;

    @Mock
    private TotemConverter converter;

    @InjectMocks
    private TotemController controller;

    private Totem totem;
    private TotemDto totemDto;

    @BeforeEach
    void setUp() {
        // Configura um objeto Totem para ser usado em todos os testes
        totem = new Totem(1, "Urca", "em frente a Unirio");
        totemDto = new TotemDto("Urca", "em frente a Unirio");
    }

    @Test
    void getTotens_Success() {
        // Mock do comportamento do serviço para retornar uma lista de totens
        when(service.getAll()).thenReturn(List.of(totem));
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<List<Totem>> response = controller.getTotens();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void postTotem_Success() {
        // Mock do comportamento do conversor e do serviço para salvar um totem
        when(converter.dtoToEntity(any(TotemDto.class))).thenReturn(totem);
        when(service.save(any(Totem.class))).thenReturn(totem);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Totem> response = controller.postTotem(totemDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(totem, response.getBody());
    }

    @Test
    void postTotem_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do conversor e do serviço para salvar um totem
        when(converter.dtoToEntity(any(TotemDto.class))).thenReturn(totem);
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.save(any(Totem.class))).thenThrow(new InvalidActionException("Dados do totem inválidos"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.postTotem(totemDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados do totem inválidos", exception.getMessage());
    }

    @Test
    void putTotem_Success() {
        // Mock do comportamento do conversor e do serviço para salvar um totem
        when(converter.dtoToEntity(any(TotemDto.class))).thenReturn(totem);
        when(service.updateTotem(anyInt(), any(Totem.class))).thenReturn(totem);

        // Chama o método do controller e verifica o resultado
        ResponseEntity<Totem> response = controller.putTotem(1, totemDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(totem, response.getBody());
    }

    @Test
    void putTotem_InvalidData_ThrowsInvalidActionException() {
        when(converter.dtoToEntity(any(TotemDto.class))).thenReturn(totem);
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.updateTotem(anyInt(), any(Totem.class))).thenThrow(new InvalidActionException("Dados do totem inválidos"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.putTotem(1, totemDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados do totem inválidos", exception.getMessage());
    }

    @Test
    void putTotem_NotFound() {
        when(converter.dtoToEntity(any(TotemDto.class))).thenReturn(totem);
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.updateTotem(anyInt(), any(Totem.class))).thenThrow(new NotFoundException("Totem não encontrada"));
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.putTotem(1, totemDto);
        });

        // Verifica a mensagem da exceção
        assertEquals("Totem não encontrada", exception.getMessage());
    }

    @Test
    void deleteTotem_Success() {
        // Mock do comportamento do serviço para excluir um totem
        doNothing().when(service).deleteTotem(anyInt());
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<Void> response = controller.deleteTotem(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTotem_NotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        doThrow(new NotFoundException("Totem não encontrada")).when(service).deleteTotem(anyInt());
        
        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.deleteTotem(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Totem não encontrada", exception.getMessage());
    }

    @Test
    void getTrancasByTotemId_Success() {
        // Cria uma Tranca para o teste
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "OCUPADA", null, 0);

        // Mock do comportamento do serviço para retornar uma lista de trancas
        when(service.getTrancasByTotem(anyInt())).thenReturn(List.of(tranca));
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<List<Tranca>> response = controller.getTrancasByTotemId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getTrancasByTotemId_TrancaNotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.getTrancasByTotem(anyInt())).thenThrow(new NotFoundException("Nenhuma tranca encontrada"));

        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getTrancasByTotemId(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Nenhuma tranca encontrada", exception.getMessage());
    }

    @Test
    void getTrancasByTotemId_TotemNotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.getTrancasByTotem(anyInt())).thenThrow(new NotFoundException("Totem não existe"));

        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getTrancasByTotemId(2);
        });

        // Verifica a mensagem da exceção
        assertEquals("Totem não existe", exception.getMessage());
    }

    @Test
    void getBicicletasByTotem_Success() {
        // Cria uma Bicicleta para o teste
        Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "DISPONÍVEL", null, 0);

        // Mock do comportamento do serviço para retornar uma lista de bicicletas
        when(service.getBicicletasByTotem(anyInt())).thenReturn(List.of(bicicleta));
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<List<Bicicleta>> response = controller.getBicicletasByTotem(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getBicicletasByTotem_BicicletaNotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.getBicicletasByTotem(anyInt())).thenThrow(new NotFoundException("Nenhuma bicicleta encontrada"));

        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getBicicletasByTotem(1);
        });

        // Verifica a mensagem da exceção
        assertEquals("Nenhuma bicicleta encontrada", exception.getMessage());
    }

    @Test
    void getBicicletasByTotem_TotemNotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.getBicicletasByTotem(anyInt())).thenThrow(new NotFoundException("Totem não existe"));

        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getBicicletasByTotem(2);
        });

        // Verifica a mensagem da exceção
        assertEquals("Totem não existe", exception.getMessage());
    }
}


