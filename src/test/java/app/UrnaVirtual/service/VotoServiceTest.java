package app.UrnaVirtual.service;

import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.entity.Eleitor.StatusEleitor;
import app.UrnaVirtual.entity.Voto;
import app.UrnaVirtual.entity.Candidato.StatusCandidato;
import app.UrnaVirtual.repository.CandidatoRepository;
import app.UrnaVirtual.repository.EleitorRepository;
import app.UrnaVirtual.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private EleitorRepository eleitorRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @InjectMocks
    private VotoService votoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVotarEleitorInapto() {
        Eleitor eleitorInapto = new Eleitor();
        eleitorInapto.setStatusEleitor(StatusEleitor.INATIVO);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitorInapto));

        Voto voto = new Voto();
        voto.setCandidatoPrefeito(new Candidato());
        voto.setCandidatoVereador(new Candidato());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.votar(voto, 1L);
        });

        assertEquals("Eleitor inapto para votação", exception.getMessage());
    }

    @Test
    public void testRealizarApuracao() {
        Candidato candidatoPrefeito = new Candidato();
        candidatoPrefeito.setIdCandidato(1L);
        candidatoPrefeito.setFuncao(1);
        candidatoPrefeito.setStatusCandidato(Candidato.StatusCandidato.ATIVO);

        Candidato candidatoVereador = new Candidato();
        candidatoVereador.setIdCandidato(2L);
        candidatoVereador.setFuncao(2);
        candidatoVereador.setStatusCandidato(Candidato.StatusCandidato.ATIVO);

        when(candidatoRepository.findByFuncaoAndStatusCandidato(1, Candidato.StatusCandidato.ATIVO))
                .thenReturn(List.of(candidatoPrefeito));

        when(candidatoRepository.findByFuncaoAndStatusCandidato(2, Candidato.StatusCandidato.ATIVO))
                .thenReturn(List.of(candidatoVereador));

        when(votoRepository.countVotosByCandidatoPrefeito(1L)).thenReturn(10);
        when(votoRepository.countVotosByCandidatoVereador(2L)).thenReturn(20);

        var apuracao = votoService.realizarApuracao();

        assertNotNull(apuracao);
        assertEquals(10, apuracao.getCandidatosPrefeito().get(0).getVotosApurados());
        assertEquals(20, apuracao.getCandidatosVereador().get(0).getVotosApurados());
    }

    @Test
    public void testVotarCandidatoPrefeitoInvalido() {
        Eleitor eleitorApto = new Eleitor();
        eleitorApto.setStatusEleitor(StatusEleitor.APTO);

        Candidato candidatoVereador = new Candidato();
        candidatoVereador.setFuncao(2);

        Voto voto = new Voto();
        voto.setCandidatoPrefeito(candidatoVereador);

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitorApto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.votar(voto, 1L);
        });

        assertEquals("O candidato escolhido para prefeito é um candidato a vereador. Refaça a requisição!", exception.getMessage());
    }
    
    @Test
    public void testVotarCandidatoVereadorInvalido() {
        Eleitor eleitorApto = new Eleitor();
        eleitorApto.setStatusEleitor(StatusEleitor.APTO);

        Candidato candidatoPrefeito = new Candidato();
        candidatoPrefeito.setFuncao(1);

        Candidato candidatoPrefeitoComoVereador = new Candidato();
        candidatoPrefeitoComoVereador.setFuncao(1);

        Voto voto = new Voto();
        voto.setCandidatoPrefeito(candidatoPrefeito);
        voto.setCandidatoVereador(candidatoPrefeitoComoVereador);

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitorApto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.votar(voto, 1L);
        });

        assertEquals("O candidato escolhido para vereador é um candidato a prefeito. Refaça a requisição!", exception.getMessage());
    }
    
    @Test
    public void testVotarEleitorBloqueado() {
        Eleitor eleitorBloqueado = new Eleitor();
        eleitorBloqueado.setStatusEleitor(StatusEleitor.BLOQUEADO);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitorBloqueado));

        Voto voto = new Voto();
        voto.setCandidatoPrefeito(new Candidato());
        voto.setCandidatoVereador(new Candidato());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            votoService.votar(voto, 1L);
        });

        assertEquals("Eleitor inapto para votação", exception.getMessage());
    }
}
