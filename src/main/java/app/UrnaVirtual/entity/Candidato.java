package app.UrnaVirtual.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Column
    @NotBlank
    @NotNull
    private String nomeCompleto;

    @Column
    @CPF
    @NotNull
    private String cpf;

    @Column
    @NotNull
    private Integer numeroCandidato;

    @Column
    @NotNull
    @Min(value = 1, message = "Função inválida, deve ser 1 para prefeito ou 2 para vereador")
    @Max(value = 2, message = "Função inválida, deve ser 1 para prefeito ou 2 para vereador")
    private Integer funcao;
    
    //STATUS CANDIDATO:
    /*TRUE - APTO
     *FALSE - INAPTO
    */
    @Column
    private Boolean statusCandidato = true ;

    @Transient
    private Integer votosApurados;

}
