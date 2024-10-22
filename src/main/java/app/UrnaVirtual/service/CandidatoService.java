package app.UrnaVirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.entity.Candidato.StatusCandidato;
import app.UrnaVirtual.repository.CandidatoRepository;

@Service
public class CandidatoService {
    
    @Autowired
    private CandidatoRepository candidatoRepository;

    public String save(Candidato candidato) {
        candidato.setStatusCandidato(StatusCandidato.ATIVO); 
        this.candidatoRepository.save(candidato);
        return "Candidato cadastrado com sucesso.";
    }

    public String update(Candidato candidato, long id) {
        candidato.setIdCandidato(id);
        this.candidatoRepository.save(candidato);
        return "Candidato atualizado com sucesso";
    }

    public Candidato findById(long id) {
        Optional<Candidato> optional = this.candidatoRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Candidato> findAllCandidatos() {
        return this.candidatoRepository.findAll();
    }

    public String delete(long id) {
        Candidato candidato = this.findById(id);
        if (candidato != null) {
            candidato.setStatusCandidato(StatusCandidato.INATIVO);
            this.candidatoRepository.save(candidato);
            return "Candidato definido como INATIVO!";
        }
        return "Candidato não encontrado.";
    }

    public List<Candidato> findCandidatosPrefeitoAtivos() {
        return candidatoRepository.findCandidatosPrefeitoAtivos();
    }

    public List<Candidato> findCandidatosVereadorAtivos() {
        return candidatoRepository.findCandidatosVereadorAtivos();
    }
}
