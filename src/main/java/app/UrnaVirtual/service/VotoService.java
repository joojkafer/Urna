package app.UrnaVirtual.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.UrnaVirtual.entity.Apuracao;
import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.entity.Voto;
import app.UrnaVirtual.entity.Candidato.StatusCandidato;
import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.entity.Eleitor.StatusEleitor;
import app.UrnaVirtual.repository.VotoRepository;
import app.UrnaVirtual.repository.CandidatoRepository;
import app.UrnaVirtual.repository.EleitorRepository;

//VotoService.java
@Service
public class VotoService {

 @Autowired
 private VotoRepository votoRepository;

 @Autowired
 private EleitorRepository eleitorRepository;

 @Autowired
 private CandidatoRepository candidatoRepository;

 @Transactional
 public String votar(Voto voto, Long idEleitor) {
     Eleitor eleitor = eleitorRepository.findById(idEleitor)
             .orElseThrow(() -> new IllegalArgumentException("Eleitor não encontrado"));

     if (!eleitor.getStatusEleitor().equals(StatusEleitor.APTO)) {
         throw new IllegalArgumentException("Eleitor inapto para votação");
     }

     Integer prefeitoNumero = voto.getCandidatoPrefeitoNumero();
     Integer vereadorNumero = voto.getCandidatoVereadorNumero();

     if (prefeitoNumero == null && vereadorNumero == null) {
         voto.setCandidatoPrefeito(null);
         voto.setCandidatoVereador(null);
     } else if (prefeitoNumero != null && vereadorNumero != null) {
         Candidato candidatoPrefeito = candidatoRepository.findByNumeroCandidato(prefeitoNumero)
                 .orElseThrow(() -> new IllegalArgumentException("Candidato a prefeito não encontrado"));
         Candidato candidatoVereador = candidatoRepository.findByNumeroCandidato(vereadorNumero)
                 .orElseThrow(() -> new IllegalArgumentException("Candidato a vereador não encontrado"));

         if (candidatoPrefeito.getFuncao() != 1) {
             throw new IllegalArgumentException("O candidato escolhido para prefeito não é um candidato a prefeito.");
         }

         if (candidatoVereador.getFuncao() != 2) {
             throw new IllegalArgumentException("O candidato escolhido para vereador não é um candidato a vereador.");
         }

         voto.setCandidatoPrefeito(candidatoPrefeito);
         voto.setCandidatoVereador(candidatoVereador);
     } else {
         throw new IllegalArgumentException("Para votar, forneça ambos os números dos candidatos ou vote em branco.");
     }

     votoRepository.save(voto);
     eleitor.setStatusEleitor(StatusEleitor.VOTOU);
     eleitorRepository.save(eleitor);

     return voto.getComprovanteHash();
 }

 @Transactional
 public Apuracao realizarApuracao() {
     List<Candidato> candidatosPrefeito = candidatoRepository.findByFuncaoAndStatusCandidato(1, StatusCandidato.ATIVO);
     List<Candidato> candidatosVereador = candidatoRepository.findByFuncaoAndStatusCandidato(2, StatusCandidato.ATIVO);

     candidatosPrefeito.forEach(candidato -> {
         int votosPrefeito = votoRepository.countVotosByCandidatoPrefeito_IdCandidato(candidato.getIdCandidato());
         candidato.setVotosApurados(votosPrefeito);
     });

     candidatosVereador.forEach(candidato -> {
         int votosVereador = votoRepository.countVotosByCandidatoVereador_IdCandidato(candidato.getIdCandidato());
         candidato.setVotosApurados(votosVereador);
     });

     int votosBrancos = votoRepository.countByCandidatoPrefeitoIsNullAndCandidatoVereadorIsNull();

     Apuracao apuracao = new Apuracao();
     apuracao.setCandidatosPrefeito(candidatosPrefeito);
     apuracao.setCandidatosVereador(candidatosVereador);
     apuracao.setTotalVotos((int) votoRepository.count());
     apuracao.setVotosBrancos(votosBrancos);

     return apuracao;
 }
}