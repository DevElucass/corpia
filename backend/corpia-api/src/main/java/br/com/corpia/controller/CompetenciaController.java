package br.com.corpia.controller;

import br.com.corpia.service.CompetenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competencias")
public class CompetenciaController {

    @Autowired
    private CompetenciaService competenciaService;

    @PostMapping("/analisar")
    public List<String> analisar(@RequestBody String texto) {

        return competenciaService.identificarCompetencias(texto);
    }
}