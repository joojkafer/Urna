package app.UrnaVirtual.service;

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.repository.EleitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EleitorServiceTest {

    @Mock
    private EleitorRepository eleitorRepository;

    @InjectMocks
    private EleitorService eleitorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveEleitor() {
        Eleitor eleitor = new Eleitor();
        eleitor.setNomeCompleto("Maria Souza");
        eleitor.setCpf("12345678909");
        eleitor.setProfissao("Professora");

        when(eleitorRepository.save(any(Eleitor.class))).thenReturn(eleitor);

        String response = eleitorService.save(eleitor);

        assertEquals("Eleitor cadastrado com sucesso.", response);
        verify(eleitorRepository, times(1)).save(eleitor);
    }

    @Test
    public void testFindByIdEleitor() {
        Eleitor eleitor = new Eleitor();
        eleitor.setIdEleitor(1L);
        eleitor.setNomeCompleto("Maria Souza");

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor));

        Eleitor foundEleitor = eleitorService.findById(1L);

        assertNotNull(foundEleitor);
        assertEquals(1L, foundEleitor.getIdEleitor());
        assertEquals("Maria Souza", foundEleitor.getNomeCompleto());
    }

    @Test
    public void testDeleteEleitor() {
        Eleitor eleitor = new Eleitor();
        eleitor.setIdEleitor(1L);
        eleitor.setStatusEleitor(Eleitor.StatusEleitor.APTO);

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor));

        String result = eleitorService.delete(1L);

        assertEquals("Eleitor definido como INATIVO!", result);
        verify(eleitorRepository, times(1)).save(eleitor);
    }
    
    @Test
    public void testFindByIdEleitorNotFound() {
        when(eleitorRepository.findById(1L)).thenReturn(Optional.empty());

        Eleitor foundEleitor = eleitorService.findById(1L);

        assertNull(foundEleitor);
    }
    
    @Test
    public void testDeleteEleitorNotFound() {
        when(eleitorRepository.findById(1L)).thenReturn(Optional.empty());

        String result = eleitorService.delete(1L);

        assertEquals("Eleitor não encontrado.", result);
        verify(eleitorRepository, never()).save(any(Eleitor.class));
    }
}
