package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BicicletaServiceTest {

    @Mock
    private BicicletaRepository bicicletaRepository;

    @InjectMocks
    private BicicletaService bicicletaService;

    private Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "Caloi", "Mountain Bike", "2022", 123, "Disponível");
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
        // Configura a bicicleta com dados inválidos
        Bicicleta invalidBicicleta = new Bicicleta(null, null, null, null, null, null);

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            bicicletaService.save(invalidBicicleta);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados da bicicleta inválidos", exception.getMessage());
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
        Bicicleta novaBicicleta = new Bicicleta(1, "Caloi", "Mountain Bike", "2023", 124, "Em Uso");
        
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
        // Configura o mock do repository para encontrar uma bicicleta
        when(bicicletaRepository.findById(anyInt())).thenReturn(Optional.of(bicicleta));

        // Configura a nova bicicleta com dados inválidos
        Bicicleta invalidBicicleta = new Bicicleta(null, null, null, null, null, null);

        // Verifica se a exceção InvalidActionException é lançada
        InvalidActionException exception = assertThrows(InvalidActionException.class, () -> {
            bicicletaService.updateBicicleta(1, invalidBicicleta);
        });

        // Verifica a mensagem da exceção
        assertEquals("Dados da bicicleta inválidos", exception.getMessage());
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
}

