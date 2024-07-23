package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.totem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TotemRepositoryTest {

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private TotemRepository totemRepository;

    private Totem totem;

    @BeforeEach
    void setUp() {
        // Configura um objeto Totem para ser usado em todos os testes
        totem = new Totem(1, "Urca", "em frente a Unirio");
    }

    @Test
    void saveTotem_NewTotem() {
        // Mock do comportamento do gerador de ID
        when(idGenerator.idTotemGenerator()).thenReturn(1);
        
        // Chama o método save do repositório
        Totem savedTotem = totemaRepository.save(totem);
        
        // Verifica se a bicicleta foi salva corretamente com o ID gerado
        assertNotNull(savedTotem.getId());
        assertEquals(totem.getLocalizacao(), savedTotem.getlocalizacao());
    }

    @Test
    void saveTotem_UpdateExisting() {
        // Mock do comportamento do gerador de ID
        when(idGenerator.idTotemGenerator()).thenReturn(1);
        totemRepository.save(totem); // Salva a totem inicialmente
        
        // Atualiza a totem
        totem.setLocalizacao("Novo Local");
        Totem updatedTotem = totemRepository.save(totem);
        
        // Verifica se a totem foi atualizada
        assertEquals("Novo Local", updatedTotem.getLocalizacao());
    }

    @Test
    void findById_Found() {
        // Mock do comportamento do método findById do repositório
        totemRepository.save(totem);
        
        // Chama o método findById do repositório
        Optional<Totem> foundTotem = totemRepository.findById(1);
        
        // Verifica se a totem foi encontrada
        assertTrue(foundTotem.isPresent());
        assertEquals(totem.getId(), foundTotem.get().getId());
    }

    @Test
    void findById_NotFound() {
        // Chama o método findById do repositório com um ID inexistente
        Optional<Totem> foundTotem = totemRepository.findById(999);
        
        // Verifica se a totem não foi encontrada
        assertFalse(foundTotem.isPresent());
    }

   @Test
    void deleteById_Success() {
        // Configura o mock do IdGenerator para fornecer um ID
        when(idGenerator.idTotemGenerator()).thenReturn(1);

        // Salva a totem inicialmente
        totemRepository.save(totem);

        // Verifica se a totem está presente antes de deletar
        assertTrue(totemRepository.findById(1).isPresent());

        // Chama o método deleteById do repositório
        boolean result = totemRepository.deleteById(1);

        // Verifica se a totem foi removida com sucesso
        assertTrue(result);
        assertFalse(totemRepository.findById(1).isPresent());
    }

    @Test
    void deleteById_NotFound() {
        // Chama o método deleteById com um ID inexistente
        boolean result = totemRepository.deleteById(999);
        
        // Verifica se a totem não foi encontrada para remoção
        assertFalse(result);
    }
}

