package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.bicicleta;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Bicicleta;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.repository.BicicletaRepository;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.BicicletaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class BicicletaServiceTest {

    @InjectMocks
    private BicicletaService bicicletaService;

    @Mock
    private BicicletaRepository bicicletaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        Bicicleta bicicleta1 = new Bicicleta(1, "Marca1", "Modelo1", "2021", 123, "Disponível");
        Bicicleta bicicleta2 = new Bicicleta(2, "Marca2", "Modelo2", "2022", 124, "Indisponível");

        when(bicicletaRepository.findAll()).thenReturn(Arrays.asList(bicicleta1, bicicleta2));

        List<Bicicleta> bicicletas = bicicletaService.getAll();

        assertNotNull(bicicletas);
        assertEquals(2, bicicletas.size());
        verify(bicicletaRepository, times(1)).findAll();
    }
}
