package app.UrnaVirtual.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.UrnaVirtual.entity.Apuracao;
import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.entity.Voto;
import app.UrnaVirtual.repository.VotoRepository;
import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.repository.CandidatoRepository;
import app.UrnaVirtual.repository.EleitorRepository;

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

        if (!eleitor.getStatusEleitor().equals(1)) {
            throw new IllegalArgumentException("Eleitor inapto para votação");
        }

        if (voto.getCandidatoPrefeito().getFuncao() != 1) {
            throw new IllegalArgumentException("O candidato escolhido para prefeito é um candidato a vereador. Refaça a requisição!");
        }

        if (voto.getCandidatoVereador().getFuncao() != 2) {
            throw new IllegalArgumentException("O candidato escolhido para vereador é um candidato a prefeito. Refaça a requisição!");
        }

        String hash = UUID.randomUUID().toString();
        voto.setComprovanteHash(hash);

        votoRepository.save(voto);

        eleitor.setStatusEleitor(1);
        eleitorRepository.save(eleitor);

        return hash;
    }

    @Transactional
    public Apuracao realizarApuracao() {
        List<Candidato> candidatosPrefeito = candidatoRepository.findByFuncaoAndStatusCandidato(1, true);
        List<Candidato> candidatosVereador = candidatoRepository.findByFuncaoAndStatusCandidato(2, true);

        for (int i = 0; i < candidatosPrefeito.size(); i++) {
            Candidato candidato = candidatosPrefeito.get(i);
            int totalVotos = votoRepository.countVotosByCandidatoPrefeito(candidato.getIdCandidato());
            candidato.setVotosApurados(totalVotos);
        }

        for (int i = 0; i < candidatosVereador.size(); i++) {
            Candidato candidato = candidatosVereador.get(i);
            int totalVotos = votoRepository.countVotosByCandidatoVereador(candidato.getIdCandidato());
            candidato.setVotosApurados(totalVotos);
        }

        candidatosPrefeito.sort((c1, c2) -> Integer.compare(c2.getVotosApurados(), c1.getVotosApurados()));
        candidatosVereador.sort((c1, c2) -> Integer.compare(c2.getVotosApurados(), c1.getVotosApurados()));

        Apuracao apuracao = new Apuracao();
        apuracao.setTotalVotos(votoRepository.count());
        apuracao.setCandidatosPrefeito(candidatosPrefeito);
        apuracao.setCandidatosVereador(candidatosVereador);

        return apuracao;
    }
}
