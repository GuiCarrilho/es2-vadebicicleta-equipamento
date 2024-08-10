package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusBicicletaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
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
    private TotemRepository totemRepository;

    @InjectMocks
    private BicicletaService bicicletaService;

    private Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "DISPONÍVEL");
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
    void saveBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na bicicleta
        bicicleta.setAno(null);
        
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
        Bicicleta novaBicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2023", 124, "NOVA");
        
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
        Bicicleta novaBicicleta = new Bicicleta(1, "Caloi", "Mountain Bike", "2023", 124, "Em Uso");
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.updateBicicleta(1, novaBicicleta));
    }
   
@Test
    void updateBicicleta_InvalidData_ThrowsInvalidActionException() {
        // Configura uma nova bicicleta com dados inválidos
        Bicicleta novaBicicleta = new Bicicleta(1, "MarcaX", "Montanha", null, 124, "Em Uso");
        
        // Mock do comportamento do método findById do repositório
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.updateBicicleta(1, novaBicicleta));
    }

    @Test
    void deleteBicicleta_Success() {
        // Mock do comportamento do método deleteById do repositório
        when(bicicletaRepository.deleteById(anyInt())).thenReturn(true);
        
        // Verifica se a operação de exclusão não lança exceção
        assertDoesNotThrow(() -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void deleteBicicleta_NotFound_ThrowsNotFoundException() {
        // Mock do comportamento do método deleteById do repositório
        when(bicicletaRepository.deleteById(anyInt())).thenReturn(false);
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> bicicletaService.deleteBicicleta(1));
    }

    @Test
    void postStatus_Success() {
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
    // Mock do comportamento dos repositórios
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        Totem totem = new Totem(1, "Centro", "Totem principal");
    
    // Configura o mock para os repositórios
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(totem.getId());
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        assertDoesNotThrow(() -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));

        verify(totemRepository).addTrancasByTotemId(totem.getId(), tranca);
    }

    @Test
    void incluirBicicletaNaRedeTotem_IdBicicletaInvalid_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 0, 1, "Centro", "2020", "ModeloA", "TRANCAR");
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
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

        assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, null));
    }

    @Test
    void incluirBicicletaNaRedeTotem_TrancaSemTotemAssociado_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);

        assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }

    @Test
    void incluirBicicletaNaRedeTotem_StatusBicicletaInvalido_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        Bicicleta bicicletaIndisponivel = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "APOSENTADA");

        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicletaIndisponivel));
        when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);

        assertThrows(InvalidActionException.class, () -> bicicletaService.incluirBicicletaNaRedeTotem(1, 1, 1));
    }

    @Test
    void retirarBicicletaDaRedeTotem_Success() {
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        // Mock do comportamento dos repositórios
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);
        when(totemRepository.removeTrancaByTotemId(anyInt(), any(Tranca.class))).thenReturn(true);

        // Chama o método retirarBicicletaDaRedeTotem do serviço
        assertDoesNotThrow(() -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_InvalidAction_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 0, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        // Mock do comportamento dos repositórios
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "REPARO_SOLICITADO"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_TrancaNaoEncontrada_ThrowsInvalidActionException() {
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_IdBicicletaNaoCorresponde_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 2, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

        assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_FuncionarioNaoExiste_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

        assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, null, "APOSENTADA"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_TrancaSemTotemAssociado_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);

        assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }

    @Test
    void retirarBicicletaDaRedeTotem_StatusAcaoReparadorInvalido_ThrowsInvalidActionException() {
        Tranca tranca = new Tranca(1, 1, 1, "Centro", "2020", "ModeloA", "TRANCAR");
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));
        when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);

        assertThrows(InvalidActionException.class, () -> bicicletaService.retirarBicicletaDaRedeTotem(1, 1, 1, "INEXISTENTE"));
    }
}

