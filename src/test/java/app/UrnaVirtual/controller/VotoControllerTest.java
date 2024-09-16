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

import app.UrnaVirtual.entity.Apuracao;
import app.UrnaVirtual.entity.Voto;
import app.UrnaVirtual.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Voto voto;

    @BeforeEach
    public void setUp() {
        voto = new Voto();
    }

    @Test
    public void testVotar() throws Exception {
        when(votoService.votar(any(Voto.class), eq(1L))).thenReturn("comprovante-hash");

        mockMvc.perform(post("/voto/votar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Voto registrado com sucesso. Comprovante: comprovante-hash"));
    }

    @Test
    public void testRealizarApuracao() throws Exception {
        Apuracao apuracao = new Apuracao();
        apuracao.setTotalVotos(100);

        when(votoService.realizarApuracao()).thenReturn(apuracao);

        mockMvc.perform(get("/voto/realizarApuracao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVotos").value(100));
    }
    
    @Test
    public void testVotarEleitorInapto() throws Exception {
        when(votoService.votar(any(Voto.class), eq(1L)))
                .thenThrow(new IllegalArgumentException("Eleitor inapto para votação"));

        mockMvc.perform(post("/voto/votar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: Eleitor inapto para votação"));
    }
}
