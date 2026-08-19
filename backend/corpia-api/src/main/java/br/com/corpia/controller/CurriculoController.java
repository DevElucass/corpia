package br.com.corpia.controller;

import br.com.corpia.model.Curriculo;
import br.com.corpia.service.CurriculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curriculos")
public class CurriculoController {

    @Autowired
    private CurriculoService curriculoService;

    @PostMapping
    public ResponseEntity<Curriculo> salvar(
            @RequestBody Curriculo curriculo) {

        Curriculo novoCurriculo = curriculoService.salvar(curriculo);

        return ResponseEntity.ok(novoCurriculo);
    }

    @GetMapping
    public ResponseEntity<List<Curriculo>> listarTodos() {

        return ResponseEntity.ok(
                curriculoService.listarTodos()
        );
    }
}