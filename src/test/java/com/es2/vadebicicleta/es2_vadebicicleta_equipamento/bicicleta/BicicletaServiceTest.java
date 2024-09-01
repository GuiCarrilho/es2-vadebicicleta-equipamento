package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.EnderecoEmail;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Funcionario;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao.AluguelClient;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.integracao.ExternoClient;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TrancaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BicicletaServiceTest {

    @Mock
    private BicicletaRepository bicicletaRepository;

    @Mock
    private TrancaRepository trancaRepository;

    @Mock
    private TrancaService trancaService;

    @Mock
    private TotemRepository totemRepository;

    @Mock
    private AluguelClient aluguelClient;

    @Mock
    private ExternoClient externoClient;

    @InjectMocks
    private BicicletaService bicicletaService;

    private Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "NOVA", null, 0);
    }

    @Test
    void saveBicicleta_Success() {
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);
        
        // Chama o método save do serviço
        Bicicleta savedBicicleta = bicicletaService.save(bicicleta);
        
        // Verifica se a bicicleta retornada não é nula e se o ID está presente
        assertNotNull(savedBicicleta);
        assertEquals(bicicleta.getId(), savedBicicleta.getId());
    }

    @Test
    void saveBicicleta_AnoInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na bicicleta
        bicicleta.setAno(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_NumeroInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na bicicleta
        bicicleta.setNumero(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_ModeloInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na bicicleta
        bicicleta.setModelo(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_MarcaInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na bicicleta
        bicicleta.setMarca(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_StatusInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na bicicleta
        bicicleta.setStatus(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_AnoEmptyData_ThrowsInvalidActionException() {
    // Configura dados inválidos na bicicleta
        bicicleta.setAno("");
         // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }
    
    @Test
    void saveBicicleta_ModeloEmptyData_ThrowsInvalidActionException() {
    // Configura dados inválidos na bicicleta
        bicicleta.setModelo("");  // Define o ano como uma string vazia
         // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_MarcaEmptyData_ThrowsInvalidActionException() {
    // Configura dados inválidos na bicicleta
        bicicleta.setMarca("");  // Define o ano como uma string vazia
         // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void saveBicicleta_StatusEmptyData_ThrowsInvalidActionException() {
    // Configura dados inválidos na bicicleta
        bicicleta.setStatus("");  // Define o ano como uma string vazia
         // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.save(bicicleta));
    }

    @Test
    void getAllBicicletas_Success() {
        // Configura o repositório para retornar uma lista contendo a bicicleta
        List<Bicicleta> bicicletas = Collections.singletonList(bicicleta);
        when(bicicletaRepository.findAll()).thenReturn(bicicletas);
        
        // Chama o método getAll do serviço
        List<Bicicleta> foundBicicletas = bicicletaService.getAll();
        
        // Verifica se a lista retornada contém a bicicleta esperada
        assertEquals(1, foundBicicletas.size());
    }

    @Test
    void getBicicletaById_Success() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        
        // Chama o método getById do serviço
        Bicicleta foundBicicleta = bicicletaService.getById(1);
        
        // Verifica se a bicicleta retornada não é nula e se o ID está presente
        assertNotNull(foundBicicleta);
        assertEquals(bicicleta.getId(), foundBicicleta.getId());
    }

    @Test
    void getBicicletaById_NotFound_ThrowsNotFoundException() {
        // Configura o repositório para retornar um Optional vazio
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.getById(1));
    }

    @Test
    void updateBicicleta_Success() {
        // Configura uma nova bicicleta para atualizar
        Bicicleta novaBicicleta = new Bicicleta(1, "MarcaY", "Corrida", "2023", 124, "NOVA", LocalDateTime.now(), 0);
        
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(novaBicicleta);
        
        // Chama o método updateBicicleta do serviço
        Bicicleta updatedBicicleta = bicicletaService.updateBicicleta(1, novaBicicleta);
        
        // Verifica se a bicicleta retornada não é nula e se os dados foram atualizados
        assertNotNull(updatedBicicleta);
        assertEquals(novaBicicleta.getAno(), updatedBicicleta.getAno());
    }

    @Test
    void updateBicicleta_NotFound_ThrowsNotFoundException() {
        // Configura o repositório para retornar um Optional vazio
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // Configura uma nova bicicleta para atualizar
        Bicicleta novaBicicleta = new Bicicleta(1, "Caloi", "Mountain Bike", "2023", 124, "Em Uso", LocalDateTime.now(), 1);
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.updateBicicleta(1, novaBicicleta));
    }
   
    @Test
    void updateBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Configura uma nova bicicleta com dados inválidos
        Bicicleta novaBicicleta = new Bicicleta(1, "MarcaX", "Montanha", null, 124, "NOVA", null, 0);
        
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.updateBicicleta(1, novaBicicleta));
    }


    @Test
    void deleteBicicleta_Success() {
        // Alterar status da bicicleta para APOSENTADA
        bicicleta.setStatus("APOSENTADA");

        // Mock do comportamento do repository.findById
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        // Mock do comportamento do trancaRepository.findTrancaByBicicleta
        when(trancaRepository.findTrancaByBicicleta(anyInt())).thenReturn(true);

        // Mock do comportamento do repository.deleteById
        when(bicicletaRepository.deleteById(anyInt())).thenReturn(true);

        // Verifica se a operação de exclusão não lança exceção
        assertDoesNotThrow(() -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void deleteBicicleta_NotFound_ThrowsNotFoundException() {
        // Mock para bicicleta não encontrada
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void deleteBicicleta_InvalidStatus_ThrowsNotFoundException() {
        // Bicicleta já possui status "NOVA" configurado no setUp()

        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void deleteBicicleta_NotLinkedToTranca_ThrowsNotFoundException() {
        // Alterar status da bicicleta para APOSENTADA
        bicicleta.setStatus("APOSENTADA");

        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        // Mock para a bicicleta não estar associada a nenhuma tranca
        when(trancaRepository.findTrancaByBicicleta(anyInt())).thenReturn(false);

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void deleteBicicleta_DeleteFailed_ThrowsNotFoundException() {
        // Alterar status da bicicleta para APOSENTADA
        bicicleta.setStatus("APOSENTADA");

        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        // Mock para a bicicleta estar associada a uma tranca
        when(trancaRepository.findTrancaByBicicleta(anyInt())).thenReturn(true);

        // Mock para falha ao deletar a bicicleta
        when(bicicletaRepository.deleteById(anyInt())).thenReturn(false);

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void postStatus_Success_EM_REPARO() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);

        // Chama o método postStatus do serviço
        Bicicleta updatedBicicleta = bicicletaService.postStatus(1, StatusBicicletaEnum.EM_REPARO);

        // Verifica se a bicicleta retornada não é nula e se o status foi atualizado
        assertNotNull(updatedBicicleta);
        assertEquals("EM_REPARO", updatedBicicleta.getStatus());
    }

    @Test
    void postStatus_Success_DISPONIVEL() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);

        // Chama o método postStatus do serviço
        Bicicleta updatedBicicleta = bicicletaService.postStatus(1, StatusBicicletaEnum.DISPONIVEL);

        // Verifica se a bicicleta retornada não é nula e se o status foi atualizado
        assertNotNull(updatedBicicleta);
        assertEquals("DISPONIVEL", updatedBicicleta.getStatus());
    }

    @Test
    void postStatus_Success_EM_USO() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);

        // Chama o método postStatus do serviço
        Bicicleta updatedBicicleta = bicicletaService.postStatus(1, StatusBicicletaEnum.EM_USO);

        // Verifica se a bicicleta retornada não é nula e se o status foi atualizado
        assertNotNull(updatedBicicleta);
        assertEquals("EM_USO", updatedBicicleta.getStatus());
    }

    @Test
    void postStatus_Success_NOVA() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);

        // Chama o método postStatus do serviço
        Bicicleta updatedBicicleta = bicicletaService.postStatus(1, StatusBicicletaEnum.NOVA);

        // Verifica se a bicicleta retornada não é nula e se o status foi atualizado
        assertNotNull(updatedBicicleta);
        assertEquals("NOVA", updatedBicicleta.getStatus());
    }

    @Test
    void postStatus_Success_APOSENTADA() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);

        // Chama o método postStatus do serviço
        Bicicleta updatedBicicleta = bicicletaService.postStatus(1, StatusBicicletaEnum.APOSENTADA);

        // Verifica se a bicicleta retornada não é nula e se o status foi atualizado
        assertNotNull(updatedBicicleta);
        assertEquals("APOSENTADA", updatedBicicleta.getStatus());
    }

    @Test
    void postStatus_Success_REPARO_SOLICITADO() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        // Mock do comportamento do método save do repositório
        when(bicicletaRepository.save(any(Bicicleta.class))).thenReturn(bicicleta);

        // Chama o método postStatus do serviço
        Bicicleta updatedBicicleta = bicicletaService.postStatus(1, StatusBicicletaEnum.REPARO_SOLICITADO);

        // Verifica se a bicicleta retornada não é nula e se o status foi atualizado
        assertNotNull(updatedBicicleta);
        assertEquals("REPARO_SOLICITADO", updatedBicicleta.getStatus());
    }

    @Test
    void postStatus_NotFound_ThrowsNotFoundException() {
        // Mock do comportamento do método findById do repositório para retornar um Optional vazio
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.postStatus(1, StatusBicicletaEnum.EM_REPARO));
    }

    @Test
    void postStatus_InvalidAction_ThrowsInvalidActionException() {
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.postStatus(1, StatusBicicletaEnum.TESTE));
    }

    @Test
    void incluirBicicletaNaRedeTotem_Success() {
    Tranca tranca = new Tranca(1, 0, 235, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "ModeloY", "2022", 123, "NOVA", null, 0);
    Funcionario funcionario = new Funcionario(1, "funcionario@example.com");
    EnderecoEmail email = new EnderecoEmail("Teste", 1L, "Mensagem teste", "teste@gmail.com");

    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(funcionario);
    when(externoClient.enviarEmail(any(EnderecoEmail.class))).thenReturn(email);
    when(trancaService.trancar(anyInt(), anyInt())).thenReturn(tranca);

    assertDoesNotThrow(() -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));

    // Verifique se o email foi enviado
    verify(externoClient).enviarEmail(any(EnderecoEmail.class));
    }
    
    @Test
    void incluirBicicletaNaRedeTotem_IdBicicletaInvalid_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void incluirBicicletaNaRedeTotem_TrancaNaoEncontrada_ThrowsInvalidActionException() {
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());
    assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void incluirBicicletaNaRedeTotem_FuncionarioNaoExiste_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 0, 1, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(null);

    assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }

    @Test
    void incluirBicicletaNaRedeTotem_FuncionarioIgual_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 0, 0, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "ModeloY", "2022", 123, "EM_REPARO", LocalDateTime.now(), 1); // Funcionário 2
    Funcionario funcionario = new Funcionario(1, "funcionario@example.com"); // Funcionário 1
    EnderecoEmail email = new EnderecoEmail("Teste", 1L, "Mensagem teste", "teste@gmail.com");

    // Mock do comportamento dos repositórios e serviços
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(funcionario);
    when(externoClient.enviarEmail(any(EnderecoEmail.class))).thenReturn(email);
    when(trancaService.trancar(anyInt(), anyInt())).thenReturn(tranca);


    // Chamada ao método e verificação de exceção
    assertDoesNotThrow(() -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));

    // Verifique se o email foi enviado
    verify(externoClient).enviarEmail(any(EnderecoEmail.class));
}

    @Test
    void incluirBicicletaNaRedeTotem_FuncionarioDiferente_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 0, 0, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "ModeloY", "2022", 123, "EM_REPARO", LocalDateTime.now(), 0); // Funcionário 2
    Funcionario funcionario = new Funcionario(1, "funcionario@example.com"); // Funcionário 1

    // Mock do comportamento dos repositórios e serviços
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(funcionario);

    // Chamada ao método e verificação de exceção
    assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
}
    
    @Test
    void incluirBicicletaNaRedeTotem_TrancaSemTotemAssociado_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 0, 1, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);

    assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void incluirBicicletaNaRedeTotem_StatusBicicletaInvalido_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 0, 1, "Centro", "2020", "ModeloA", "LIVRE", null, 0);
    Bicicleta bicicletaIndisponivel = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "APOSENTADA", null, 0);

    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicletaIndisponivel));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);

    assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void retirarBicicletaDaRedeTotem_Success() {
    Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "OCUPADA", null, 0);
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "ModeloY", "2022", 123, "REPARO_SOLICITADO", null, 0);
    Funcionario funcionario = new Funcionario(1, "funcionario@example.com");
    EnderecoEmail email = new EnderecoEmail("Teste", 1L, "Mensagem teste", "teste@gmail.com");

    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(funcionario);
    when(trancaService.destrancar(anyInt(), anyInt())).thenReturn(tranca);
    when(externoClient.enviarEmail(any(EnderecoEmail.class))).thenReturn(email);

    assertDoesNotThrow(() -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));

    // Verifique se o email foi enviado
    verify(externoClient).enviarEmail(any(EnderecoEmail.class));
    }
    
    @Test
    void retirarBicicletaDaRedeTotem_InvalidAction_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR", null, 0);
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

    assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "REPARO_SOLICITADO"));
    }
    
    @Test
    void retirarBicicletaDaRedeTotem_TrancaNaoEncontrada_ThrowsInvalidActionException() {
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());
    assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }
    
    @Test
    void retirarBicicletaDaRedeTotem_IdBicicletaNaoCorresponde_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 2, 1, "Centro", "2020", "ModeloA", "TRANCAR", null, 0);
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

    assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_FuncionarioNaoExiste_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR", null, 0);
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(null);

    assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }
    
    @Test
    void retirarBicicletaDaRedeTotem_TrancaSemTotemAssociado_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "OCUPADA", null, 0);
    // Configura o comportamento esperado dos mocks
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);

    // Executa o teste e verifica se a exceção é lançada
    assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
}

    
@Test
void retirarBicicletaDaRedeTotem_StatusAcaoReparadorInvalido_ThrowsInvalidActionException() {
    Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "OCUPADA", null, 0);
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "ModeloY", "2022", 123, "REPARO_SOLICITADO", null, 0);

    // Configura o comportamento esperado dos mocks
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);
    when(aluguelClient.obterFuncionario(anyInt())).thenReturn(new Funcionario(1, "funcionario@example.com"));

    // Executa o teste e verifica se a exceção é lançada
    assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "INEXISTENTE"));
}
}

