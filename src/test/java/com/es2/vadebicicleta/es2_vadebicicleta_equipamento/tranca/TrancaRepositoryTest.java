package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.tranca;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TrancaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrancaRepositoryTest {

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private TrancaRepository trancaRepository;

    private Tranca tranca;

    @BeforeEach
    void setUp() {
        // Configura um objeto Tranca para ser usado em todos os testes
        tranca = new Tranca(1, 0, 123, "Unirio", "2019", "Corrida", "Trancar");
    }

    @Test
    void saveTranca_NewTranca() {
        // Mock do comportamento do gerador de ID
        when(idGenerator.idBicicletaGenerator()).thenReturn(1);
        
        // Chama o método save do repositório
        Tranca savedTranca = trancaRepository.save(tranca);
        
        // Verifica se a tranca foi salva corretamente com o ID gerado
        assertNotNull(savedTranca.getId());
        assertEquals(tranca.getMarca(), savedTranca.getMarca());
    }

    @Test
    void saveTranca_UpdateExisting() {
        // Mock do comportamento do gerador de ID
        when(idGenerator.idTrancaGenerator()).thenReturn(1);
        trancaRepository.save(tranca); // Salva a tranca inicialmente
        
        // Atualiza a tranca
        tranca.setLocalizacao("Novo Local");
        Tranca updatedTranca = trancaRepository.save(tranca);
        
        // Verifica se a tranca foi atualizada
        assertEquals("Novo Local", updatedTranca.getLocalizacao());
    }

    @Test
    void findById_Found() {
        // Mock do comportamento do método findById do repositório
        trancaRepository.save(tranca);
        
        // Chama o método findById do repositório
        Optional<Tranca> foundTranca = trancaRepository.findById(1);
        
        // Verifica se a tranca foi encontrada
        assertTrue(foundTranca.isPresent());
        assertEquals(tranca.getId(), foundTranca.get().getId());
    }

    @Test
    void findById_NotFound() {
        // Chama o método findById do repositório com um ID inexistente
        Optional<Tranca> foundTranca = trancaRepository.findById(999);
        
        // Verifica se a tranca não foi encontrada
        assertFalse(foundTranca.isPresent());
    }

    @Test
    void deleteById_Success() {
        // Mock do comportamento do método deleteById
        trancaRepository.save(tranca); // Salva a tranca inicialmente
        
        // Chama o método deleteById do repositório
        boolean result = trancaRepository.deleteById(1);
        
        // Verifica se a tranca foi removida com sucesso
        assertTrue(result);
        assertFalse(trancaRepository.findById(1).isPresent());
    }

    @Test
    void deleteById_NotFound() {
        // Chama o método deleteById com um ID inexistente
        boolean result = trancaRepository.deleteById(999);
        
        // Verifica se a tranca não foi encontrada para remoção
        assertFalse(result);
    }
}
