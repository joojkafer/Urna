package app.UrnaVirtual.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.service.CandidatoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

@WebMvcTest(CandidatoController.class)
public class CandidatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidatoService candidatoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Candidato candidato;

    @BeforeEach
    public void setUp() {
        candidato = new Candidato();
        candidato.setIdCandidato(1L);
        candidato.setNomeCompleto("João Silva");
        candidato.setCpf("12345678909");
        candidato.setFuncao(1);
    }

    @Test
    public void testSaveCandidato() throws Exception {
        when(candidatoService.save(any(Candidato.class))).thenReturn("Candidato cadastrado com sucesso.");

        mockMvc.perform(post("/candidato/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidato cadastrado com sucesso."));
    }

    @Test
    public void testFindById() throws Exception {
        when(candidatoService.findById(1L)).thenReturn(candidato);

        mockMvc.perform(get("/candidato/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCandidato").value(1L))
                .andExpect(jsonPath("$.nomeCompleto").value("João Silva"));
    }

    @Test
    public void testDeleteCandidato() throws Exception {
        when(candidatoService.delete(1L)).thenReturn("Candidato definido como INATIVO!");

        mockMvc.perform(delete("/candidato/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidato definido como INATIVO!"));
    }

    @Test
    public void testFindAllAtivo() throws Exception {
        when(candidatoService.findAll()).thenReturn(Arrays.asList(candidato));

        mockMvc.perform(get("/candidato/findAllAtivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCandidato").value(1L))
                .andExpect(jsonPath("$[0].nomeCompleto").value("João Silva"));
    }
    
    
    @Test
    public void testSaveCandidatoWithException() throws Exception {
        when(candidatoService.save(any(Candidato.class))).thenThrow(new RuntimeException("Erro no serviço"));

        mockMvc.perform(post("/candidato/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(candidato)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: Erro no serviço"));
    }

}
