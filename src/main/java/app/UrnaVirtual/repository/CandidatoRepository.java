package app.UrnaVirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.entity.Candidato.StatusCandidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

	Optional<Candidato> findByNumeroCandidato(Integer numeroCandidato);
    List<Candidato> findByStatusCandidato(StatusCandidato statusCandidato);
    List<Candidato> findByFuncaoAndStatusCandidato(Integer funcao, StatusCandidato statusCandidato);

    default List<Candidato> findCandidatosPrefeitoAtivos() {
        return findByFuncaoAndStatusCandidato(1, StatusCandidato.ATIVO);
    }

    default List<Candidato> findCandidatosVereadorAtivos() {
        return findByFuncaoAndStatusCandidato(2, StatusCandidato.ATIVO);
    }
    
}
