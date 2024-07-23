package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.tranca;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
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

    @InjectMocks
    private TrancaService trancaService;

    private Tranca tranca;

    @BeforeEach
    void setUp() {
        // Configura um objeto Tranca para ser usado em todos os testes
        tranca = new Tranca(1, 0, 123, "Unirio", "2019", "Corrida", "Trancar");
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
    void saveTranca_InvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na tranca
        tranca.setAno(null);
        
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
        Tranca novaTranca = new Tranca(1, 0, 123, "Unirio", "2019", "Montanha", "Trancar");
        
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
        Tranca novaTranca = new Tranca(1, 0, 123, "Unirio", "2019", "Montanha", "Trancar");
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> trancaService.updateTranca(1, novaTranca));
    }
   
@Test
    void updateTranca_InvalidData_ThrowsInvalidActionException() {
        // Configura uma nova tranca com dados inválidos
        Tranca novaTranca = new Tranca(1, 0, 123, "Unirio", "2019", null, "Trancar");
        
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
}

