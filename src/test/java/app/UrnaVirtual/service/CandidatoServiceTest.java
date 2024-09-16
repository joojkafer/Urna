package app.UrnaVirtual.service;

import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandidatoServiceTest {

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private CandidatoService candidatoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCandidato() {
        Candidato candidato = new Candidato();
        candidato.setNomeCompleto("João Silva");
        candidato.setCpf("12345678909");
        candidato.setFuncao(1);

        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        String response = candidatoService.save(candidato);

        assertEquals("Candidato cadastrado com sucesso.", response);
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    public void testFindByIdCandidato() {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1L);
        candidato.setNomeCompleto("João Silva");

        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        Candidato foundCandidato = candidatoService.findById(1L);

        assertNotNull(foundCandidato);
        assertEquals(1L, foundCandidato.getIdCandidato());
        assertEquals("João Silva", foundCandidato.getNomeCompleto());
    }

    @Test
    public void testDeleteCandidato() {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(1L);
        candidato.setStatusCandidato(Candidato.StatusCandidato.ATIVO); 


        when(candidatoRepository.findById(1L)).thenReturn(Optional.of(candidato));

        String result = candidatoService.delete(1L);

        assertEquals("Candidato definido como INATIVO!", result);
        verify(candidatoRepository, times(1)).save(candidato);
    }

    @Test
    public void testFindAllAtivos() {
        Candidato candidato1 = new Candidato();
        candidato1.setNomeCompleto("João");
        candidato1.setStatusCandidato(Candidato.StatusCandidato.ATIVO);

        when(candidatoRepository.findByStatusCandidato(Candidato.StatusCandidato.ATIVO))
        .thenReturn(List.of(candidato1));

        List<Candidato> ativos = candidatoService.findAll();

        assertEquals(1, ativos.size());
        assertEquals("João", ativos.get(0).getNomeCompleto());
    }
    
    @Test
    public void testFindByIdCandidatoNotFound() {
        when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        Candidato foundCandidato = candidatoService.findById(1L);

        assertNull(foundCandidato);
    }
    
    @Test
    public void testDeleteCandidatoNotFound() {
        when(candidatoRepository.findById(1L)).thenReturn(Optional.empty());

        String result = candidatoService.delete(1L);

        assertEquals("Candidato não encontrado.", result);
        verify(candidatoRepository, never()).save(any(Candidato.class));
    }
    
    @Test
    public void testFindByFuncaoAndStatusCandidato() {
        Candidato candidato = new Candidato();
        candidato.setFuncao(1);
        candidato.setStatusCandidato(Candidato.StatusCandidato.ATIVO);

        when(candidatoRepository.findByFuncaoAndStatusCandidato(1, Candidato.StatusCandidato.ATIVO))
                .thenReturn(Collections.singletonList(candidato));

        List<Candidato> resultado = candidatoRepository.findByFuncaoAndStatusCandidato(1, Candidato.StatusCandidato.ATIVO);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getFuncao());
    }
}
