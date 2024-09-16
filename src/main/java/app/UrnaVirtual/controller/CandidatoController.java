package app.UrnaVirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.UrnaVirtual.entity.Candidato;
import app.UrnaVirtual.service.CandidatoService;

@RestController
@RequestMapping("/candidato")
public class CandidatoController {
    
    @Autowired
    private CandidatoService candidatoService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Candidato candidato) {
        try {
            String mensagem = this.candidatoService.save(candidato);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Candidato candidato, @PathVariable long id) {
        try {
            String mensagem = this.candidatoService.update(candidato, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Candidato> findById(@PathVariable long id) {
        try {
            Candidato candidato = this.candidatoService.findById(id);
            if (candidato != null) {
                return new ResponseEntity<>(candidato, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllAtivo")
    public ResponseEntity<List<Candidato>> findAllAtivo() {
        try {
            List<Candidato> lista = this.candidatoService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = this.candidatoService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
