package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.totem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TotemServiceTest {

    @Mock
    private TotemRepository totemRepository;

    @InjectMocks
    private TotemService totemService;

    private Totem totem;

    @BeforeEach
    void setUp() {
        // Configura um objeto Totem para ser usado em todos os testes
        totem = new Totem(1, "Urca", "em frente a Unirio");
    }

    @Test
    void saveTotem_Success() {
        // Mock do comportamento do método save do repositório
        when(totemRepository.save(any(Totem.class))).thenReturn(totem);
        
        // Chama o método save do serviço
        Totem savedTotem = totemService.save(totem);
        
        // Verifica se a totem retornada não é nula e se o ID está presente
        assertNotNull(savedTotem);
        assertEquals(totem.getId(), savedTotem.getId());
    }

    @Test
    void saveTotem_InvalidData_ThrowsInvalidActionException() {
        // Configura dados inválidos na totem
        totem.setDescricao(null);
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> totemService.save(totem));
    }

    
    @Test
    void getAllTotems_Success() {
        // Configura o repositório para retornar uma lista contendo a totem
        List<Totem> totens = Collections.singletonList(totem);
        when(totemRepository.findAll()).thenReturn(totens);
        
        // Chama o método getAll do serviço
        List<Totem> foundTotens = totemService.getAll();
        
        // Verifica se a lista retornada contém a totem esperada
        assertEquals(1, foundTotens.size());
    }

    @Test
    void getTotemById_Success() {
        // Mock do comportamento do método findById do repositório
        when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
        
        // Chama o método getById do serviço
        Totem foundTotem = totemService.getById(1);
        
        // Verifica se a totem retornada não é nula e se o ID está presente
        assertNotNull(foundTotem);
        assertEquals(totem.getId(), foundTotem.getId());
    }

    @Test
    void getTotemById_NotFound_ThrowsNotFoundException() {
        // Configura o repositório para retornar um Optional vazio
        when(totemRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> totemService.getById(1));
    }

    @Test
    void updateTotem_Success() {
        // Configura uma novo totem para atualizar
        Totem novoTotem = new Totem(1, "Méier", "no Leão do Méier");
        
        // Mock do comportamento do método findById do repositório
        when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
        // Mock do comportamento do método save do repositório
        when(totemRepository.save(any(Totem.class))).thenReturn(novoTotem);
        
        // Chama o método updateTotem do serviço
        Totem updatedTotem = totemService.updateTotem(1, novoTotem);
        
        // Verifica se a totem retornada não é nula e se os dados foram atualizados
        assertNotNull(updatedTotem);
        assertEquals(novoTotem.getDescricao(), updatedTotem.getDescricao());
    }

    @Test
    void updateTotem_NotFound_ThrowsNotFoundException() {
        // Configura o repositório para retornar um Optional vazio
        when(totemRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        // Configura uma novo totem para atualizar
        Totem novoTotem = new Totem(1, "Méier", "no Leão do Méier");
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> totemService.updateTotem(1, novoTotem));
    }
   
    @Test
    void updateTotem_InvalidData_ThrowsInvalidActionException() {
        // Configura uma novo totem com dados inválidos
        Totem novoTotem = new Totem(1, "Méier", null);
        
        // Mock do comportamento do método findById do repositório
        when(totemRepository.findById(anyInt())).thenReturn(Optional.of(totem));
        
        // Verifica se a exceção InvalidActionException é lançada
        assertThrows(InvalidActionException.class, () -> totemService.updateTotem(1, novoTotem));
    }

    @Test
    void deleteTotem_Success() {
        // Mock do comportamento do método deleteById do repositório
        when(totemRepository.deleteById(anyInt())).thenReturn(true);
        
        // Verifica se a operação de exclusão não lança exceção
        assertDoesNotThrow(() -> totemService.deleteTotem(1));
    }

    @Test
    void deleteTotem_NotFound_ThrowsNotFoundException() {
        // Mock do comportamento do método deleteById do repositório
        when(totemRepository.deleteById(anyInt())).thenReturn(false);
        
        // Verifica se a exceção NotFoundException é lançada
        assertThrows(NotFoundException.class, () -> totemService.deleteTotem(1));
    }
}

