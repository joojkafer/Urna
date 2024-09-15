package app.UrnaVirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.UrnaVirtual.entity.Voto;
import app.UrnaVirtual.service.VotoService;

@RestController
@RequestMapping("/voto")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping("/votar/{idEleitor}")
    public ResponseEntity<String> votar(@RequestBody Voto voto, @PathVariable Long idEleitor) {
        try {
            String hashComprovante = this.votoService.votar(voto, idEleitor);
            return new ResponseEntity<>("Voto registrado com sucesso. Comprovante: " + hashComprovante, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/realizarApuracao")
    public ResponseEntity<?> realizarApuracao() {
        try {
            var apuracao = this.votoService.realizarApuracao();
            return new ResponseEntity<>(apuracao, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
