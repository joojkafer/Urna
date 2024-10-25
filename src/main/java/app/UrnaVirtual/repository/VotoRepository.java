package app.UrnaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.UrnaVirtual.entity.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
	int countVotosByCandidatoPrefeito_IdCandidato(Long idCandidato);
    int countVotosByCandidatoVereador_IdCandidato(Long idCandidato);
    
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.candidatoPrefeito IS NULL AND v.candidatoVereador IS NULL")
    int countByCandidatoPrefeitoIsNullAndCandidatoVereadorIsNull();
}
