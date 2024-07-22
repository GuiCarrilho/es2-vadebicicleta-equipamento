package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BicicletaRepositoryTest {

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private BicicletaRepository bicicletaRepository;

    private Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        // Configura um objeto Bicicleta para ser usado em todos os testes
        bicicleta = new Bicicleta(1, "Caloi", "Mountain Bike", "2022", 123, "Disponível");
    }

    @Test
    void saveBicicleta_NewBicicleta() {
        // Mock do comportamento do gerador de ID
        when(idGenerator.idBicicletaGenerator()).thenReturn(1);
        
        // Chama o método save do repositório
        Bicicleta savedBicicleta = bicicletaRepository.save(bicicleta);
        
        // Verifica se a bicicleta foi salva corretamente com o ID gerado
        assertNotNull(savedBicicleta.getId());
        assertEquals(bicicleta.getMarca(), savedBicicleta.getMarca());
    }

    @Test
    void saveBicicleta_UpdateExisting() {
        // Mock do comportamento do gerador de ID
        when(idGenerator.idBicicletaGenerator()).thenReturn(1);
        bicicletaRepository.save(bicicleta); // Salva a bicicleta inicialmente
        
        // Atualiza a bicicleta
        bicicleta.setMarca("Nova Marca");
        Bicicleta updatedBicicleta = bicicletaRepository.save(bicicleta);
        
        // Verifica se a bicicleta foi atualizada
        assertEquals("Nova Marca", updatedBicicleta.getMarca());
    }

    @Test
    void findById_Found() {
        // Mock do comportamento do método findById do repositório
        bicicletaRepository.save(bicicleta);
        
        // Chama o método findById do repositório
        Optional<Bicicleta> foundBicicleta = bicicletaRepository.findById(1);
        
        // Verifica se a bicicleta foi encontrada
        assertTrue(foundBicicleta.isPresent());
        assertEquals(bicicleta.getId(), foundBicicleta.get().getId());
    }

    @Test
    void findById_NotFound() {
        // Chama o método findById do repositório com um ID inexistente
        Optional<Bicicleta> foundBicicleta = bicicletaRepository.findById(999);
        
        // Verifica se a bicicleta não foi encontrada
        assertFalse(foundBicicleta.isPresent());
    }

    @Test
    void deleteById_Success() {
        // Mock do comportamento do método deleteById
        bicicletaRepository.save(bicicleta); // Salva a bicicleta inicialmente
        
        // Chama o método deleteById do repositório
        boolean result = bicicletaRepository.deleteById(1);
        
        // Verifica se a bicicleta foi removida com sucesso
        assertTrue(result);
        assertFalse(bicicletaRepository.findById(1).isPresent());
    }

    @Test
    void deleteById_NotFound() {
        // Chama o método deleteById com um ID inexistente
        boolean result = bicicletaRepository.deleteById(999);
        
        // Verifica se a bicicleta não foi encontrada para remoção
        assertFalse(result);
    }
}

