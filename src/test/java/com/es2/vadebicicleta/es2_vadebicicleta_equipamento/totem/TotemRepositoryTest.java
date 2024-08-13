package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.totem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Tranca;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.TotemRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
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

    @Mock
    private BicicletaRepository bicicletaRepository;

    @InjectMocks
    private TotemRepository totemRepository;

    private Totem totem;
    private Tranca tranca;
    private Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        // Configura um objeto Totem para ser usado em todos os testes
        totem = new Totem(1, "Urca", "em frente a Unirio");
        // Configura um objeto Tranca
        tranca = new Tranca(1, 1, 101, "local1", "2024", "modelo1", "ABERTA");
        // Configura um objeto Bicicleta
        bicicleta = new Bicicleta(1, "Marca A", "Modelo A", "2024", 123, "DISPONIVEL");
    }

    @Test
    void saveTotem_NewTotem() {
        
        // Chama o método save do repositório
        Totem savedTotem = totemRepository.save(totem);
        
        // Verifica se o totem foi salvo corretamente com o ID gerado
        assertNotNull(savedTotem.getId());
        assertEquals(totem.getLocalizacao(), savedTotem.getLocalizacao());
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
    void findAll_Success() {
        // Esperado
        List<Totem> totensExperados = new ArrayList<>();
        totemRepository.save(totem);
    
        totensExperados.add(totem);

        // Chama o método findAll do repositório
        List<Totem> allTotens = totemRepository.findAll();

        // Verifica se o tamanho da lista e os elementos são os esperados
        assertEquals(totensExperados.size(), allTotens.size());
        assertTrue(allTotens.containsAll(totensExperados));
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

    @Test
    void addTrancasByTotemId_Success() {
        // Adiciona a tranca ao totem
        totemRepository.addTrancasByTotemId(1, tranca);

        // Verifica se a tranca foi adicionada corretamente
        List<Tranca> trancas = totemRepository.findTrancasByTotemId(1);
        assertNotNull(trancas);
        assertTrue(trancas.contains(tranca));
    }

    @Test
    void removeTrancaByTotemId_Success() {
        // Adiciona a tranca ao totem
        totemRepository.addTrancasByTotemId(1, tranca);

        // Remove a tranca
        boolean result = totemRepository.removeTrancaByTotemId(1, tranca);

        // Verifica se a tranca foi removida corretamente
        assertTrue(result);
        assertFalse(totemRepository.findTrancasByTotemId(1).contains(tranca));
    }

    @Test
    void removeTrancaByTotemId_NotFound() {
        // Tenta remover uma tranca de um totem que não existe
        boolean result = totemRepository.removeTrancaByTotemId(2, tranca);

        // Verifica se a operação retornou false
        assertFalse(result);
    }

    @Test
    void findTrancasByTotemId_Success() {
    // Configura a tranca no totem
    totemRepository.addTrancasByTotemId(2, tranca);

    // Encontra as trancas pelo ID do totem
    List<Tranca> trancas = totemRepository.findTrancasByTotemId(2);

    // Verifica se a lista de trancas não é nula e contém a tranca esperada
    assertNotNull(trancas);
    assertEquals(1, trancas.size());
    assertTrue(trancas.contains(tranca));
    }

    @Test
    void findTrancasByTotemId_NotFound() {
    // Tenta encontrar Trancas para um ID de Totem inexistente
    List<Tranca> trancas = totemRepository.findTrancasByTotemId(999);

    // Verifica se a lista retornada é vazia
    assertNotNull(trancas);
    assertTrue(trancas.isEmpty());
}

    @Test
    void findTotemByTranca_Found() {
        // Adiciona a tranca ao totem
        totemRepository.addTrancasByTotemId(1, tranca);

        // Encontra o totem pela tranca
        Integer idTotem = totemRepository.findTotemByTranca(tranca);

        // Verifica se o ID do totem é o esperado
        assertNotNull(idTotem);
        assertEquals(1, idTotem);
    }

    @Test
    void findTotemByTranca_NotFound() {
        // Tenta encontrar o totem pela tranca que não está associada a nenhum totem
        Integer idTotem = totemRepository.findTotemByTranca(tranca);

        // Verifica se o ID do totem é null
        assertNull(idTotem);
    }

    @Test
    void findBicicletasByTotemId_Success() {
        // Adiciona a tranca com uma bicicleta ao totem
        tranca.setBicicleta(1);
        totemRepository.addTrancasByTotemId(1, tranca);
        // Mock do comportamento do método findById do repositório de bicicletas
        when(bicicletaRepository.findById(1)).thenReturn(Optional.of(bicicleta));

        // Encontra as bicicletas pelo ID do totem
        List<Bicicleta> bicicletas = totemRepository.findBicicletasByTotemId(1);

        // Verifica se a lista de bicicletas é a esperada
        assertNotNull(bicicletas);
        assertTrue(bicicletas.contains(bicicleta));
    }

    @Test
    void findBicicletasByTotemId_NoTrancas_ThrowsNotFoundException() {
        // Tenta encontrar bicicletas de um totem sem trancas associadas
        assertThrows(NotFoundException.class, () -> totemRepository.findBicicletasByTotemId(1));
    }

    @Test
    void findBicicletasByTotemId_BicicletaNotFound_ThrowsNotFoundException() {
        // Adiciona a tranca com uma bicicleta ao totem
        tranca.setBicicleta(1);
        totemRepository.addTrancasByTotemId(1, tranca);
        // Mock do comportamento do método findById do repositório de bicicletas
        when(bicicletaRepository.findById(1)).thenReturn(Optional.empty());

        // Tenta encontrar as bicicletas pelo ID do totem
        assertThrows(NotFoundException.class, () -> totemRepository.findBicicletasByTotemId(1));
    }
}
