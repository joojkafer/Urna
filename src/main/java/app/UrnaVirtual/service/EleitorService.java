package app.UrnaVirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.entity.Eleitor.StatusEleitor;
import app.UrnaVirtual.repository.EleitorRepository;

@Service
public class EleitorService {
    
    @Autowired
    private EleitorRepository eleitorRepository;

    public String save(Eleitor eleitor) {
        if (eleitor.getCpf() == null || eleitor.getEmail() == null) {
            eleitor.setStatusEleitor(StatusEleitor.PENDENTE);
        } else {
            eleitor.setStatusEleitor(StatusEleitor.APTO);
        }
        this.eleitorRepository.save(eleitor);
        return "Eleitor cadastrado com sucesso.";
    }

    public String update(Eleitor eleitor, long id) {
        Eleitor existente = this.findById(id);
        if (existente != null && existente.getStatusEleitor() == StatusEleitor.INATIVO) {
            eleitor.setStatusEleitor(StatusEleitor.INATIVO);
        } else if (eleitor.getCpf() == null || eleitor.getEmail() == null) {
            eleitor.setStatusEleitor(StatusEleitor.PENDENTE);
        } else {
            eleitor.setStatusEleitor(StatusEleitor.APTO);
        }
        eleitor.setIdEleitor(id);
        this.eleitorRepository.save(eleitor);
        return "Eleitor atualizado com sucesso";
    }

    public Eleitor findById(long id) {
        Optional<Eleitor> optional = this.eleitorRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Eleitor> findAll() {
        return this.eleitorRepository.findByStatusEleitor(StatusEleitor.APTO);
    }

    public String delete(long id) {
        Eleitor eleitor = this.findById(id);
        if (eleitor != null && eleitor.getStatusEleitor() != StatusEleitor.VOTOU) {
            eleitor.setStatusEleitor(StatusEleitor.INATIVO);
            this.eleitorRepository.save(eleitor);
            return "Eleitor definido como INATIVO!";
        } else if (eleitor != null) {
            throw new IllegalArgumentException("Usuário já votou. Não foi possível inativá-lo.");
        }
        return "Eleitor não encontrado.";
    }
}
