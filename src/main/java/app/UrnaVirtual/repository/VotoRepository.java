package app.UrnaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.UrnaVirtual.entity.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("SELECT COUNT(v) FROM Voto v WHERE v.candidatoPrefeito.idCandidato = :idCandidato")
    int countVotosByCandidatoPrefeito(Long idCandidato);

    @Query("SELECT COUNT(v) FROM Voto v WHERE v.candidatoVereador.idCandidato = :idCandidato")
    int countVotosByCandidatoVereador(Long idCandidato);
}
