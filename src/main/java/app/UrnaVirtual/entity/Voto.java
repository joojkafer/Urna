package app.UrnaVirtual.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoto;

    @Column(nullable = false)
    private LocalDateTime dataHoraVotacao;

    @ManyToOne
    @JoinColumn(name = "idCandidatoPrefeito", nullable = false)
    private Candidato candidatoPrefeito;

    @ManyToOne
    @JoinColumn(name = "idCandidatoVereador", nullable = false)
    private Candidato candidatoVereador;

    @Column(nullable = false, unique = true)
    private String comprovanteHash;

    @Transient
    private Integer candidatoPrefeitoNumero;

    @Transient
    private Integer candidatoVereadorNumero;

    @PrePersist
    public void prePersist() {
        this.dataHoraVotacao = LocalDateTime.now();
        this.comprovanteHash = gerarHash();
    }

    private String gerarHash() {
        return UUID.randomUUID().toString();
    }
}
