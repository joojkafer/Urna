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

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.service.EleitorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

@WebMvcTest(EleitorController.class)
public class EleitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EleitorService eleitorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Eleitor eleitor;

    @BeforeEach
    public void setUp() {
        eleitor = new Eleitor();
        eleitor.setIdEleitor(1L);
        eleitor.setNomeCompleto("Maria Souza");
        eleitor.setCpf("12345678909");
        eleitor.setProfissao("Professora");
    }

    @Test
    public void testSaveEleitor() throws Exception {
        when(eleitorService.save(any(Eleitor.class))).thenReturn("Eleitor cadastrado com sucesso.");

        mockMvc.perform(post("/eleitor/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eleitor)))
                .andExpect(status().isOk())
                .andExpect(content().string("Eleitor cadastrado com sucesso."));
    }

    @Test
    public void testFindById() throws Exception {
        when(eleitorService.findById(1L)).thenReturn(eleitor);

        mockMvc.perform(get("/eleitor/findById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEleitor").value(1L))
                .andExpect(jsonPath("$.nomeCompleto").value("Maria Souza"));
    }

    @Test
    public void testDeleteEleitor() throws Exception {
        when(eleitorService.delete(1L)).thenReturn("Eleitor definido como INATIVO!");

        mockMvc.perform(delete("/eleitor/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eleitor definido como INATIVO!"));
    }

    @Test
    public void testFindAll() throws Exception {
        when(eleitorService.findAll()).thenReturn(Arrays.asList(eleitor));

        mockMvc.perform(get("/eleitor/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEleitor").value(1L))
                .andExpect(jsonPath("$[0].nomeCompleto").value("Maria Souza"));
    }
    
    @Test
    public void testSaveEleitorWithInvalidData() throws Exception {
        Eleitor eleitorInvalido = new Eleitor();
        eleitorInvalido.setNomeCompleto("");

        mockMvc.perform(post("/eleitor/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eleitorInvalido)))
                .andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void testDeleteEleitorWithException() throws Exception {
        when(eleitorService.delete(1L)).thenThrow(new RuntimeException("Erro no serviço"));

        mockMvc.perform(delete("/eleitor/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Erro: Erro no serviço"));
    }

}
