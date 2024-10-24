package app.UrnaVirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.UrnaVirtual.entity.Eleitor;
import app.UrnaVirtual.entity.Eleitor.StatusEleitor;
import app.UrnaVirtual.service.EleitorService;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/eleitor")
@CrossOrigin("*")
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Eleitor eleitor) {
        try {
            String mensagem = this.eleitorService.save(eleitor);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Eleitor eleitor, @PathVariable long id) {
        try {
            String mensagem = this.eleitorService.update(eleitor, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Eleitor> findById(@PathVariable long id) {
        try {
            Eleitor eleitor = this.eleitorService.findById(id);
            if (eleitor != null) {
                return new ResponseEntity<>(eleitor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/findEleitorByCPF/{cpf}")
    public ResponseEntity<Eleitor> findEleitorByCPF(@PathVariable String cpf) {
        try {
            Eleitor eleitor = eleitorService.findEleitorByCPF(cpf);
            if (eleitor != null) {
                return new ResponseEntity<>(eleitor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllAptos")
    public ResponseEntity<List<Eleitor>> findAllAptos() {
        try {
            List<Eleitor> lista = this.eleitorService.findAllAptos();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<List<Eleitor>> findAll() {
        try {
            List<Eleitor> lista = this.eleitorService.findAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        try {
            String mensagem = this.eleitorService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}