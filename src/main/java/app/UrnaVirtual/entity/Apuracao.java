package app.UrnaVirtual.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apuracao {

    private long totalVotos;
    
    private List<Candidato> candidatosPrefeito;
    
    private List<Candidato> candidatosVereador;
    
    private int votosBrancos;

}
