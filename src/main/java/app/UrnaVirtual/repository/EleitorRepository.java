package app.UrnaVirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.entity.Eleitor.StatusEleitor;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

    List<Eleitor> findByStatusEleitor(StatusEleitor statusEleitor);

    default List<Eleitor> findEleitoresInativos() {
        return findByStatusEleitor(StatusEleitor.INATIVO);
    }
}
