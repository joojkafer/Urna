package app.UrnaVirtual.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "idCandidatoPrefeito")
    private Candidato candidatoPrefeito;

    @ManyToOne
    @JoinColumn(name = "idCandidatoVereador")
    private Candidato candidatoVereador;

    @Column
    private String comprovanteHash;

    @PrePersist
    public void prePersist() {
        this.dataHoraVotacao = LocalDateTime.now();
        this.comprovanteHash = gerarHash();
    }

    private String gerarHash() {
        return Integer.toHexString(hashCode()) + System.currentTimeMillis();
    }
}
