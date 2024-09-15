package app.UrnaVirtual.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class Eleitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEleitor;

    @Column
    @NotBlank
    @NotNull
    private String nomeCompleto;
    
    @Column
    @CPF
    private String cpf;

    @Column
    @NotBlank
    @NotNull
    private String profissao;

    @Column
    @Pattern(regexp = "^\\(?(\\d{2})\\)?\\s?(9\\d{4})-?(\\d{4})$", message = "Telefone celular inválido, deve seguir o padrão (XX) XXXXX-XXXX")
    @NotBlank
    @NotNull
    private String telefoneCelular;
    
    @Column
    @Pattern(regexp = "^\\(?(\\d{2})\\)?\\s?(\\d{4,5})-?(\\d{4})$", message = "Telefone fixo inválido, deve seguir o padrão (XX) XXXX-XXXX")
    private String telefoneFixo;

    @Column
    @Email
    private String email;
    
    //STATUS ELEITOR:
    /*1 - APTO
     *2 - INATIVO
     *3 - BLOQUEADO
     *4 - PENDENTE
     *5 - VOTOU.
    */
    
    @Column
    private Integer statusEleitor;
    
}
