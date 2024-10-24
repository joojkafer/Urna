package app.UrnaVirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.entity.Eleitor.StatusEleitor;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

    List<Eleitor> findByStatusEleitor(StatusEleitor statusEleitor);
    @Query("SELECT e FROM Eleitor e WHERE e.cpf = :cpf")
    Optional<Eleitor> findByCpfManual(@Param("cpf") String cpf);
   
    default List<Eleitor> findEleitoresInativos() {
        return findByStatusEleitor(StatusEleitor.INATIVO);
    }
}
