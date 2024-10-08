package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.tranca;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

import java.util.List;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.BicicletaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TrancaController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TrancaConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.BicicletaDtoReturn;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TrancaDtoReturn;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.TrancaIncluirNaRedeRequest;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.TrancaRequest;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.request.TrancaRetirarDaRedeRequest;
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

    @Mock
    private TrancaConverter converter;

    @Mock
    private BicicletaConverter bicicletaConverter;

    @InjectMocks
    private TrancaController controller;

    private Tranca tranca;
    private TrancaDto trancaDto;
    private TrancaDtoReturn trancaDtoReturn;

    @BeforeEach
    void setUp() {
        // Configura um objeto Tranca para ser usado em todos os testes
        tranca = new Tranca(1, 0, 123, "Unirio", "2019", "Corrida", "NOVA", null, 0);
        trancaDto = new TrancaDto(123, "Unirio", "2019", "Corrida", "NOVA");
        trancaDtoReturn = new TrancaDtoReturn(1, 1, 123, "Unirio", "2019", "Corrida", "NOVA");

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
        // Mock do comportamento do conversor e do serviço para salvar uma tranca
        when(converter.dtoToEntity(any(TrancaDto.class))).thenReturn(tranca);
        when(service.save(any(Tranca.class))).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.postTranca(trancaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    @Test
    void postTranca_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do conversor para retornar uma bicicleta inválida
        when(converter.dtoToEntity(any(TrancaDto.class))).thenReturn(tranca);
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
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);

        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.getTrancaById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
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
        // Mock do comportamento do conversor e do serviço para atualizar uma tranca
        when(converter.dtoToEntity(any(TrancaDto.class))).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        when(service.updateTranca(anyInt(), any(Tranca.class))).thenReturn(tranca);

        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.putTranca(1, trancaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    @Test
    void putTranca_InvalidData_ThrowsInvalidActionException() {
        // Mock do comportamento do conversor para retornar uma tranca inválida
        when(converter.dtoToEntity(any(TrancaDto.class))).thenReturn(tranca);
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
        // Mock do comportamento do conversor para retornar uma tranca inválida
        when(converter.dtoToEntity(any(TrancaDto.class))).thenReturn(tranca);
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

    @Test
    void statusTrancar_Success_WithBicicleta() {
        // Mock do comportamento do serviço para trancar com uma bicicleta
        when(service.trancar(anyInt(), anyInt())).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        TrancaRequest request = new TrancaRequest();
        request.setIdBicicleta(1);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.statusTrancar(1, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    @Test
    void statusTrancar_Success_WithoutBicicleta() {
        // Mock do comportamento do serviço para trancar sem uma bicicleta
        when(service.trancar(anyInt(), isNull())).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.statusTrancar(1, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    void statusTrancar_NotFound() {
        when(service.trancar(anyInt(), anyInt())).thenThrow(new NotFoundException("Não encontrado"));
        TrancaRequest request = new TrancaRequest();
        request.setIdBicicleta(1);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.statusTrancar(1, request);
        });

        assertEquals("Não econtrado", exception.getMessage());
    }

    @Test
    void statusDestrancar_Success_WithBicicleta() {
        // Mock do comportamento do serviço para destrancar com uma bicicleta
        when(service.destrancar(anyInt(), anyInt())).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        TrancaRequest request = new TrancaRequest();
        request.setIdBicicleta(1);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.statusDestrancar(1, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    @Test
    void statusDestrancar_Success_WithoutBicicleta() {
        // Mock do comportamento do serviço para destrancar sem uma bicicleta
        when(service.destrancar(anyInt(), isNull())).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        
        // Chama o método do controller e verifica o resultado
        ResponseEntity<TrancaDtoReturn> response = controller.statusDestrancar(1, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    @Test
    void statusDestrancar_NotFound() {
        when(service.destrancar(anyInt(), anyInt())).thenThrow(new NotFoundException("Não encontrado"));
        TrancaRequest request = new TrancaRequest();
        request.setIdBicicleta(1);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.statusDestrancar(2, request);
        });

        assertEquals("Não encontrado", exception.getMessage());
    }

    @Test
    void postStatus_Success() {
        when(service.postStatus(anyInt(), any(StatusTrancaEnum.class))).thenReturn(tranca);
        when(converter.entityToDtoReturn(any(Tranca.class))).thenReturn(trancaDtoReturn);
        
        ResponseEntity<TrancaDtoReturn> response = controller.postStatus(1, StatusTrancaEnum.NOVA);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trancaDtoReturn, response.getBody());
    }

    @Test
    void postStatus_TrancaNotFound() {
        // Mock do comportamento do serviço para lançar a exceção NotFoundException
        when(service.postStatus(anyInt(), any(StatusTrancaEnum.class))).thenThrow(new NotFoundException("Tranca não encontrada"));

        // Verifica se a exceção NotFoundException é lançada
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.postStatus(2, StatusTrancaEnum.LIVRE);
        });

        // Verifica a mensagem da exceção
        assertEquals("Tranca não encontrada", exception.getMessage());
    }

    @Test
    void postStatus_InvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        when(service.postStatus(anyInt(), any(StatusTrancaEnum.class))).thenThrow(new InvalidActionException("Status não escolhido"));

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.postStatus(1, StatusTrancaEnum.ERRO);
        });

        // Verifica a mensagem da exceção
        assertEquals("Status não escolhido", exception.getMessage());
    }
    
    @Test
    void incluirNaRede_Success() {
        // Criação do objeto de requisição com os dados necessários
        TrancaIncluirNaRedeRequest request = new TrancaIncluirNaRedeRequest();
        request.setIdTotem(1);
        request.setIdTranca(1);
        request.setIdFuncionario(1);

        // Mock do comportamento do serviço para incluir a tranca na rede
        doNothing().when(service).incluirTrancaNaRedeTotem(1, 1, 1);

        // Chama o método do controlador passando o objeto de requisição
        ResponseEntity<Void> response = controller.incluirNaRede(request);

        // Verifica se o serviço foi chamado corretamente
        verify(service).incluirTrancaNaRedeTotem(1, 1, 1);

        // Verifica o resultado da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void incluirNaRede_InvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        doThrow(new InvalidActionException("Dados inválidos")).when(service).incluirTrancaNaRedeTotem(1, 1, 1);

        // Cria um objeto de request com os valores de teste
        TrancaIncluirNaRedeRequest request = new TrancaIncluirNaRedeRequest();
        request.setIdTotem(1);
        request.setIdTranca(1);
        request.setIdFuncionario(1);

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.incluirNaRede(request);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados inválidos", exception.getMessage());
    }
    
    @Test
    void retirarDaRede_Success() {
        // Criação do objeto de requisição com os dados necessários
        TrancaRetirarDaRedeRequest request = new TrancaRetirarDaRedeRequest();
        request.setIdTotem(1);
        request.setIdTranca(1);
        request.setIdFuncionario(1);
        request.setStatusAcaoReparador("APOSENTADA");

        // Mock do comportamento do serviço para retirar a tranca da rede
        doNothing().when(service).retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA");

        // Chama o método do controlador passando o objeto de requisição
        ResponseEntity<Void> response = controller.retirarDaRede(request);

        // Verifica se o serviço foi chamado corretamente
        verify(service).retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA");

        // Verifica o resultado da resposta
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void retirarDaRede_InvalidActionException() {
        // Mock do comportamento do serviço para lançar a exceção InvalidActionException
        doThrow(new InvalidActionException("Dados inválidos")).when(service).retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA");

        // Cria um objeto de request com os valores de teste
        TrancaRetirarDaRedeRequest request = new TrancaRetirarDaRedeRequest();
        request.setIdTotem(1);
        request.setIdTranca(1);
        request.setIdFuncionario(1);
        request.setStatusAcaoReparador("APOSENTADA");

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            controller.retirarDaRede(request);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados inválidos", exception.getMessage());
    }
    @Test
    void getBicicletaByTrancaId_Success() {
    // Configurando o comportamento esperado dos mocks
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "NOVA", null, 0);
    BicicletaDtoReturn bicicletaDtoReturn = new BicicletaDtoReturn(1, "MarcaX", "Montanha", "2022", 123, "NOVA");
    
    when(service.getBicicletaByTrancaId(anyInt())).thenReturn(bicicleta);
    when(bicicletaConverter.entityToDtoReturn(bicicleta)).thenReturn(bicicletaDtoReturn);

    // Executando o método que está sendo testado
    ResponseEntity<BicicletaDtoReturn> response = controller.getBicicletaByTrancaId(1);
    
    // Verificando o resultado
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(bicicletaDtoReturn, response.getBody());
}

    @Test
    void getBicicletaByTrancaId_NotFound() {
        when(service.getBicicletaByTrancaId(anyInt())).thenThrow(new NotFoundException("Bicicleta não encontrada"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            controller.getBicicletaByTrancaId(2);
        });

        assertEquals("Bicicleta não encontrada", exception.getMessage());
    }

}
