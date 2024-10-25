package app.UrnaVirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.UrnaVirtual.entity.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {
	int countVotosByCandidatoPrefeito_IdCandidato(Long idCandidato);
    int countVotosByCandidatoVereador_IdCandidato(Long idCandidato);
}
