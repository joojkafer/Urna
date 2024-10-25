package app.UrnaVirtual.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidato;

    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @Column(nullable = false, unique = true)
    @NotNull
    private Integer numeroCandidato;
    
    @NotEmpty
    private String email;

    @Column(nullable = false)
    @NotNull
    @Min(value = 1, message = "Função inválida, deve ser 1 para prefeito ou 2 para vereador")
    @Max(value = 2, message = "Função inválida, deve ser 1 para prefeito ou 2 para vereador")
    private Integer funcao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCandidato statusCandidato = StatusCandidato.ATIVO;

    @Transient
    private Integer votosApurados;

    public enum StatusCandidato {
        ATIVO, INATIVO
    }
}

