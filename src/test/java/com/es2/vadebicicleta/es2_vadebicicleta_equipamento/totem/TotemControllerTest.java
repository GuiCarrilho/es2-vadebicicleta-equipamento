package com.es2.vadebicicleta.es2_vadebicicleta_equipamento.totem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TotemController;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.controller.TotemConverter;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.Totem;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.domain.dto.TotemDto;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.NotFoundException;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.service.TotemService;
import com.es2.vadebicicleta.es2_vadebicicleta_equipamento.exception.InvalidActionException;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.lang.String;

@ExtendWith(MockitoExtension.class)
public class TotemControllerTest {

    @Mock
    private TotemService totemService; // Mock do serviço

    @Mock
    private TotemConverter totemConverter; // Mock do conversor

    @InjectMocks
    private TotemController totemController; // Controller a ser testado

    @Autowired
    private MockMvc mockMvc; // Objeto para simular chamadas HTTP

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(totemController).build();
    }

    @Test
    public void getTotens_ReturnsListOfTotens() throws Exception {
        // Teste para o endpoint GET /totem que retorna todos os totens
        Totem totem1 = new Totem();
        Totem totem2 = new Totem();
        List<Totem> totens = Arrays.asList(totem1, totem2);

        when(totemService.getAll()).thenReturn(totens); // Configura o mock para retornar uma lista de totens

        mockMvc.perform(get("/totem"))
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$").isArray()) // Verifica se o corpo da resposta é uma lista
                .andExpect(jsonPath("$[0]").isNotEmpty()) // Verifica se o primeiro elemento da lista não é vazio
                .andExpect(jsonPath("$[1]").isNotEmpty()); // Verifica se o segundo elemento da lista não é vazio

        verify(totemService, times(1)).getAll(); // Verifica se o método getAll foi chamado uma vez
    }

    @Test
public void getTotens_ReturnsEmptyList() throws Exception {
    // Teste para o endpoint GET /totem que retorna uma lista vazia quando não há totens
    List<Totem> emptyTotens = Arrays.asList();

    when(totemService.getAll()).thenReturn(emptyTotens); // Configura o mock para retornar uma lista vazia

    mockMvc.perform(get("/totem"))
            .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
            .andExpect(jsonPath("$").isEmpty()); // Verifica se o corpo da resposta é uma lista vazia

    verify(totemService, times(1)).getAll(); // Verifica se o método getAll foi chamado uma vez
}

    @Test
    public void postTotem_CreatesAndReturnsTotem() throws Exception {
        // Teste para o endpoint POST /totem que cria um novo totem
        TotemDto totemDto = new TotemDto();
        Totem totem = new Totem();
        Totem newTotem = new Totem();

        when(totemConverter.dtoToEntity(totemDto)).thenReturn(totem); // Configura o mock para converter DTO para entidade
        when(totemService.save(totem)).thenReturn(newTotem); // Configura o mock para salvar o totem e retornar o novo totem

        mockMvc.perform(post("/totem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(totemDto))) // Envia uma requisição POST com o corpo em JSON
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$.id").isNotEmpty()); // Verifica se o ID do novo totem não é vazio

        verify(totemService, times(1)).save(totem); // Verifica se o método save foi chamado uma vez
    }

    @Test
    public void postTotem_InvalidData_ReturnsUnprocessableEntity() throws Exception {
    // Teste para o endpoint POST /totem que retorna 422 Unprocessable Entity ao tentar criar um totem com dados inválidos
    TotemDto invalidTotemDto = new TotemDto(); // DTO inválido (por exemplo, sem descrição)

    when(totemConverter.dtoToEntity(invalidTotemDto)).thenReturn(new Totem());
    when(totemService.save(any(Totem.class))).thenThrow(new InvalidActionException("Dados do totem inválidos")); // Configura o mock para lançar exceção

    mockMvc.perform(post("/totem")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(invalidTotemDto))) // Envia uma requisição POST com o corpo em JSON
            .andExpect(status().isUnprocessableEntity()); // Verifica se o status da resposta é 422 Unprocessable Entity

    verify(totemService, times(1)).save(any(Totem.class)); // Verifica se o método save foi chamado uma vez
}

    @Test
    public void putTotem_UpdatesAndReturnsTotem() throws Exception {
        // Teste para o endpoint PUT /totem/{idTotem} que atualiza um totem existente
        Integer idTotem = 1;
        TotemDto totemDto = new TotemDto();
        Totem totem = new Totem();
        Totem updatedTotem = new Totem();

        when(totemConverter.dtoToEntity(totemDto)).thenReturn(totem); // Configura o mock para converter DTO para entidade
        when(totemService.updateTotem(idTotem, totem)).thenReturn(updatedTotem); // Configura o mock para atualizar o totem

        mockMvc.perform(put("/totem/{idTotem}", idTotem)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(totemDto))) // Envia uma requisição PUT com o corpo em JSON
                .andExpect(status().isOk()) // Verifica se o status da resposta é 200 OK
                .andExpect(jsonPath("$.id").isNotEmpty()); // Verifica se o ID do totem atualizado não é vazio

        verify(totemService, times(1)).updateTotem(idTotem, totem); // Verifica se o método updateTotem foi chamado uma vez
    }

    @Test
    public void putTotem_InvalidData_ReturnsUnprocessableEntity() throws Exception {
    // Teste para o endpoint PUT /totem/{idTotem} que retorna 422 Unprocessable Entity ao tentar atualizar um totem com dados inválidos
    Integer idTotem = 1;
    TotemDto invalidTotemDto = new TotemDto(); // DTO inválido (por exemplo, sem descrição)
    Totem totem = new Totem();

    when(totemConverter.dtoToEntity(invalidTotemDto)).thenReturn(totem); // Configura o mock para converter DTO para entidade
    when(totemService.updateTotem(idTotem, totem)).thenThrow(new InvalidActionException("Dados do totem inválidos")); // Configura o mock para lançar exceção

    mockMvc.perform(put("/totem/{idTotem}", idTotem)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(invalidTotemDto))) // Envia uma requisição PUT com o corpo em JSON
            .andExpect(status().isUnprocessableEntity()); // Verifica se o status da resposta é 422 Unprocessable Entity

    verify(totemService, times(1)).updateTotem(idTotem, totem); // Verifica se o método updateTotem foi chamado uma vez
}

    @Test
    public void putTotem_NonExistingId_ReturnsNotFound() throws Exception {
    // Teste para o endpoint PUT /totem/{idTotem} que retorna 404 Not Found ao tentar atualizar um totem inexistente
    Integer idTotem = 999;
    TotemDto totemDto = new TotemDto();
    Totem totem = new Totem();

    when(totemConverter.dtoToEntity(totemDto)).thenReturn(totem); // Configura o mock para converter DTO para entidade
    when(totemService.updateTotem(idTotem, totem)).thenThrow(NotFoundException.class); // Configura o mock para lançar exceção

    mockMvc.perform(put("/totem/{idTotem}", idTotem)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(totemDto))) // Envia uma requisição PUT com o corpo em JSON
            .andExpect(status().isNotFound()); // Verifica se o status da resposta é 404 Not Found

    verify(totemService, times(1)).updateTotem(idTotem, totem); // Verifica se o método updateTotem foi chamado uma vez
}

    @Test
    public void deleteTotem_DeletesTotem() throws Exception {
        // Teste para o endpoint DELETE /totem/{idTotem} que deleta um totem
        Integer idTotem = 1;

        mockMvc.perform(delete("/totem/{idTotem}", idTotem))
                .andExpect(status().isNotFound()); // Verifica se o status da resposta é 404 Not Found

        verify(totemService, times(1)).deleteTotem(idTotem); // Verifica se o método deleteTotem foi chamado uma vez
    }

    @Test
    public void deleteTotem_NonExistingId_ReturnsNotFound() throws Exception {
    // Teste para o endpoint DELETE /totem/{idTotem} que retorna 404 Not Found ao tentar deletar um totem inexistente
    Integer idTotem = 999;

    doThrow(new NotFoundException()).when(totemService).deleteTotem(idTotem); // Configura o mock para lançar exceção

    mockMvc.perform(delete("/totem/{idTotem}", idTotem))
            .andExpect(status().isNotFound()); // Verifica se o status da resposta é 404 Not Found

    verify(totemService, times(1)).deleteTotem(idTotem); // Verifica se o método deleteTotem foi chamado uma vez
}
}


