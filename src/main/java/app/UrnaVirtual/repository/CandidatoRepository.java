package app.UrnaVirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.UrnaVirtual.entity.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
	
	List<Candidato> findByStatusCandidato(Boolean statusCandidato);
	
	List<Candidato> findByFuncaoAndStatusCandidato(Integer funcao, Boolean statusCandidato);
}