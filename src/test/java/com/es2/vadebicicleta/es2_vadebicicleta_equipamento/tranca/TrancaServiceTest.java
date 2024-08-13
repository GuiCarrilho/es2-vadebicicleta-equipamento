package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.tranca;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.StatusTrancaEnum;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
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
class TrancaServiceTest {

    @Mock
    private TrancaRepository trancaRepository;

    @Mock
    private TotemRepository totemRepository;

    @Mock
    private BicicletaService bicicletaService;

    @InjectMocks
    private TrancaService trancaService;

    private Tranca tranca;
    private Totem totem;

    private String trancarMens = "TRANCAR";
    private String destrancarMens = "DESTRANCAR";

    @BeforeEach
    void setUp() {
        // Configura um objeto Tranca para ser usado em todos os testes
        tranca = new Tranca(1, 0, 123, "Unirio", "2019", "Corrida", "Trancar");
        totem = new Totem(1, "Centro", "Totem principal");
    }

    @Test
    void saveTranca_Success() {
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(tranca);
        
        // Chama o método save do serviço
        Tranca savedTranca = trancaService.save(tranca);
        
        // Verifica se a tranca retornada não é nula e se o ID está presente
        assertNotNull(savedTranca);
        assertEquals(tranca.getId(), savedTranca.getId());
    }

    @Test
    void saveTranca_LocalizacaoInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setLocalizacao(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_ModeloInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setModelo(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_AnoDeFabricacaoInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setAnoDeFabricacao(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_StatusInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setStatus(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_NumeroInvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setNumero(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_LocalizacaoEmptyData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setLocalizacao("");
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_ModeloEmptyData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setModelo("");
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_AnoDeFabricacaoEmptyData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setAnoDeFabricacao("");
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    @Test
    void saveTranca_StatusEmptyData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setStatus("");
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.save(tranca));
    }

    
    @Test
    void getAllTranca_Success() {
        // Configura o repositório para retornar uma lista contendo a tranca
        List<Tranca> trancas = Collections.singletonList(tranca);
        when(trancaRepository.findAll()).thenReturn(trancas);
        
        // Chama o método getAll do serviço
        List<Tranca> foundTrancas = trancaService.getAll();
        
        // Verifica se a lista retornada contém a tranca esperada
        assertEquals(1, foundTrancas.size());
    }

    @Test
    void getTrancaById_Success() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        
        // Chama o método getById do serviço
        Tranca foundTranca = trancaService.getById(1);
        
        // Verifica se a tranca retornada não é nula e se o ID está presente
        assertNotNull(foundTranca);
        assertEquals(tranca.getId(), foundTranca.getId());
    }

    @Test
    void getTrancaById_NotFound_ThrowsNotFoundException() {
        // Configura o repositório para retornar um Optional vazio
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> trancaService.getById(1));
    }

    @Test
    void updateTranca_Success() {
        // Configura uma nova tranca para atualizar
        Tranca novaTranca = new Tranca(1, 0, 123, "Unirio", "2019", "Montanha", "TRANCAR");
        
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(novaTranca);
        
        // Chama o método updateTranca do serviço
        Tranca updatedTranca = trancaService.updateTranca(1, novaTranca);
        
        // Verifica se a tranca retornada não é nula e se os dados foram atualizados
        assertNotNull(updatedTranca);
        assertEquals(novaTranca.getModelo(), updatedTranca.getModelo());
    }

    @Test
    void updateTranca_NotFound_ThrowsNotFoundException() {
        // Configura o repositório para retornar um Optional vazio
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // Configura uma nova tranca para atualizar
        Tranca novaTranca = new Tranca(1, 0, 123, "Unirio", "2019", "Montanha", "TRANCAR");
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> trancaService.updateTranca(1, novaTranca));
    }
   
@Test
    void updateTranca_InvalidData_ThrowsInvalidActionException() {
        // Configura uma nova tranca com dados inválidos
        Tranca novaTranca = new Tranca(1, 0, 123, "Unirio", "2019", null, "TRANCAR");
        
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.updateTranca(1, novaTranca));
    }

    @Test
    void deleteTranca_Success() {
        // Mock do comportamento do método deleteById do repositório
        when(trancaRepository.deleteById(anyInt())).thenReturn(true);
        
        // Verifica se a operação de exclusão não lança exceção
        assertDoesNotThrow(() -> trancaService.deleteTranca(1));
    }

    @Test
    void deleteTranca_NotFound_ThrowsNotFoundException() {
        // Mock do comportamento do método deleteById do repositório
        when(trancaRepository.deleteById(anyInt())).thenReturn(false);
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> trancaService.deleteTranca(1));
    }

    @Test
    void postStatus_Success_NOVA() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(tranca);

        // Chama o método postStatus do serviço
        Tranca updatedTranca = trancaService.postStatus(1, StatusTrancaEnum.NOVA);

        // Verifica se a tranca retornada não é nula e se o status foi atualizado
        assertNotNull(updatedTranca);
        assertEquals("NOVA", updatedTranca.getStatus());
    }

    @Test
    void postStatus_Success_APOSENTADA() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(tranca);

        // Chama o método postStatus do serviço
        Tranca updatedTranca = trancaService.postStatus(1, StatusTrancaEnum.APOSENTADA);

        // Verifica se a tranca retornada não é nula e se o status foi atualizado
        assertNotNull(updatedTranca);
        assertEquals("APOSENTADA", updatedTranca.getStatus());
    }

    @Test
    void postStatus_Success_DESTRANCAR() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(tranca);

        // Chama o método postStatus do serviço
        Tranca updatedTranca = trancaService.postStatus(1, StatusTrancaEnum.DESTRANCAR);

        // Verifica se a tranca retornada não é nula e se o status foi atualizado
        assertNotNull(updatedTranca);
        assertEquals("DESTRANCAR", updatedTranca.getStatus());
    }

    @Test
    void postStatus_Success_TRANCAR() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(tranca);

        // Chama o método postStatus do serviço
        Tranca updatedTranca = trancaService.postStatus(1, StatusTrancaEnum.TRANCAR);

        // Verifica se a tranca retornada não é nula e se o status foi atualizado
        assertNotNull(updatedTranca);
        assertEquals("TRANCAR", updatedTranca.getStatus());
    }

    @Test
    void postStatus_Success_EM_REPARO() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
        // Mock do comportamento do método save do repositório
        when(trancaRepository.save(any(Tranca.class))).thenReturn(tranca);

        // Chama o método postStatus do serviço
        Tranca updatedTranca = trancaService.postStatus(1, StatusTrancaEnum.EM_REPARO);

        // Verifica se a tranca retornada não é nula e se o status foi atualizado
        assertNotNull(updatedTranca);
        assertEquals("EM_REPARO", updatedTranca.getStatus());
    }

    @Test
    void postStatus_NotFound_ThrowsNotFoundException() {
        // Mock do comportamento do método findById do repositório para retornar um Optional vazio
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> trancaService.postStatus(1, StatusTrancaEnum.EM_REPARO));
    }

    @Test
    void postStatus_InvalidAction_ThrowsInvalidActionException() {
        // Mock do comportamento do método findById do repositório
        when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));

        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> trancaService.postStatus(1, StatusTrancaEnum.ERRO));
    }

    @Test
    void incluirTrancaNaRedeTotem_Success() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);  // Nenhum totem associado
    tranca.setStatus("NOVA");

    // Chama o método incluirTrancaNaRedeTotem do serviço
    assertDoesNotThrow(() -> trancaService.incluirTrancaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void incluirTrancaNaRedeTotem_TotemNotFound_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.incluirTrancaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void incluirTrancaNaRedeTotem_TrancaNotFound_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.incluirTrancaNaRedeTotem(1, 1, 1));
    }

    @Test
    void incluirTrancaNaRedeTotem_FuncionarioNaoExiste_ThrowsInvalidActionException() {
        assertThrows(InvalidActionException.class, () -> trancaService.incluirTrancaNaRedeTotem(1, 1, null));
    }
    
    @Test
    void incluirTrancaNaRedeTotem_TrancaAlreadyAssociated_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);  // Tranca já associada

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.incluirTrancaNaRedeTotem(1, 1, 1));
    }
    
    @Test
    void incluirTrancaNaRedeTotem_InvalidStatus_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);  // Nenhum totem associado
    tranca.setStatus("INVÁLIDO");  // Status inválido

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.incluirTrancaNaRedeTotem(1, 1, 1));
    }

    @Test
    void retirarTrancaDaRedeTotem_Success() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);  // Tranca associada

    // Mock do comportamento de remover a tranca do totem
    when(totemRepository.removeTrancaByTotemId(anyInt(), any(Tranca.class))).thenReturn(true);

    // Chama o método retirarTrancaDaRedeTotem do serviço
    assertDoesNotThrow(() -> trancaService.retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }
    
    @Test
    void retirarTrancaDaRedeTotem_TotemNotFound_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }
    
    @Test
    void retirarTrancaDaRedeTotem_TrancaNotFound_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }
    
    @Test
    void retirarTrancaDaRedeTotem_TrancaNotAssociated_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(null);  // Tranca não associada

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.retirarTrancaDaRedeTotem(1, 1, 1, "APOSENTADA"));
    }
    
    @Test
    void retirarTrancaDaRedeTotem_InvalidStatus_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(totemRepository.findTotemByTranca(any(Tranca.class))).thenReturn(1);  // Tranca associada

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.retirarTrancaDaRedeTotem(1, 1, 1, "EM_USO"));
    }

    @Test
    void getBicicletaByTrancaId_Success() {
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "DISPONÍVEL");
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaService.getById(anyInt())).thenReturn(bicicleta);

    // Chama o método getBicicletaByTrancaId do serviço
    Bicicleta result = assertDoesNotThrow(() -> trancaService.getBicicletaByTrancaId(1));
    assertEquals(bicicleta, result);
    }
    
    @Test
    void getBicicletaByTrancaId_TrancaNotFound_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.getBicicletaByTrancaId(1));
    }
    
    @Test
    void getBicicletaByTrancaId_BicicletaNotFound_ThrowsNotFoundException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    tranca.setBicicleta(null);

    // Verifica se a exceção NotFoundException é lançada
    assertThrows(NotFoundException.class, () -> trancaService.getBicicletaByTrancaId(1));
    }

    @Test
    void trancar_Success() {
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "DISPONÍVEL");
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaService.getById(anyInt())).thenReturn(bicicleta);
    tranca.setStatus(destrancarMens);

    // Chama o método trancar do serviço
    Tranca result = assertDoesNotThrow(() -> trancaService.trancar(1, 1));
    assertEquals(trancarMens, result.getStatus());
    assertEquals(1, result.getBicicleta());
    }
    
    @Test
    void trancar_TrancaNotFound_ThrowsNotFoundException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção NotFoundException é lançada
    assertThrows(NotFoundException.class, () -> trancaService.trancar(1, 1));
    }
    
    @Test
    void trancar_BicicletaAlreadyAssociated_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    tranca.setBicicleta(1); // Bicicleta já associada

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.trancar(1, 1));
    }
    
    @Test
    void trancar_TrancaAlreadyLocked_ThrowsNotFoundException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    tranca.setStatus(trancarMens); // Tranca já trancada

    // Verifica se a exceção NotFoundException é lançada
    assertThrows(NotFoundException.class, () -> trancaService.trancar(1, 1));
    }
    
    @Test
    void trancar_BicicletaNotFound_ThrowsNotFoundException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaService.getById(anyInt())).thenReturn(null); // Bicicleta não encontrada
    tranca.setStatus(destrancarMens);

    // Verifica se a exceção NotFoundException é lançada
    assertThrows(NotFoundException.class, () -> trancaService.trancar(1, 1));
    }

    @Test
    void destrancar_Success() {
    Bicicleta bicicleta = new Bicicleta(1, "MarcaX", "Montanha", "2022", 123, "DISPONÍVEL");
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    when(bicicletaService.getById(anyInt())).thenReturn(bicicleta);
    tranca.setStatus(trancarMens);
    tranca.setBicicleta(1);

    // Chama o método destrancar do serviço
    Tranca result = assertDoesNotThrow(() -> trancaService.destrancar(1, 1));
    assertEquals(destrancarMens, result.getStatus());
    assertNull(result.getBicicleta());
    }
    
    @Test
    void destrancar_TrancaNotFound_ThrowsNotFoundException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.empty());

    // Verifica se a exceção NotFoundException é lançada
    assertThrows(NotFoundException.class, () -> trancaService.destrancar(1, 1));
    }
    
    @Test
    void destrancar_BicicletaNotAssociated_ThrowsInvalidActionException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    tranca.setBicicleta(2); // Bicicleta diferente da passada

    // Verifica se a exceção InvalidActionException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.destrancar(1, 1));
    }
    
    @Test
    void destrancar_TrancaAlreadyUnlocked_ThrowsNotFoundException() {
    // Mock do comportamento dos repositórios
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    tranca.setStatus(destrancarMens); // Tranca já destrancada

    // Verifica se a exceção NotFoundException é lançada
    assertThrows(InvalidActionException.class, () -> trancaService.destrancar(1, 1));
    }
    
    @Test
    void destrancar_BicicletaNotFound_ThrowsNotFoundException() {
    // Configura o mock do repositório para retornar uma tranca válida com bicicleta associada
    when(trancaRepository.findById(anyInt())).thenReturn(Optional.of(tranca));
    tranca.setBicicleta(1); // Associa uma bicicleta à tranca
    tranca.setStatus(trancarMens);

    // Configura o mock do serviço para retornar null ao buscar a bicicleta
    when(bicicletaService.getById(anyInt())).thenReturn(null); // Bicicleta não encontrada

    // Verifica se a exceção NotFoundException é lançada quando a bicicleta não é encontrada
    assertThrows(NotFoundException.class, () -> trancaService.destrancar(1, 1));
}

}

