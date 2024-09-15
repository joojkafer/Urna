package app.UrnaVirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.repository.EleitorRepository;

@Service
public class EleitorService {
	
    @Autowired
    private EleitorRepository eleitorRepository;

    public String save(Eleitor eleitor) {
        this.eleitorRepository.save(eleitor);
        return "Eleitor cadastrado com sucesso.";
    }

    public String update(Eleitor eleitor, long id) {
    	eleitor.setIdEleitor(id);
        this.eleitorRepository.save(eleitor);
        return "Eleitor atualizado com sucesso";
    }

    public Eleitor findById(long id) {
        Optional<Eleitor> optional = this.eleitorRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Eleitor> findAll() {
        return this.eleitorRepository.findAll();
    }

    public String delete(long id) {
        this.eleitorRepository.deleteById(id);
        return "Eleitor deletado com sucesso!";
    }
}
