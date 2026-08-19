package br.com.corpia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.corpia.service.ChunkingService;
import br.com.corpia.service.ConhecimentoService;

@RestController
@RequestMapping("/conhecimento")
public class ConhecimentoController {

    private final ConhecimentoService conhecimentoService;
    private final ChunkingService chunkingService;

    public ConhecimentoController(
            ConhecimentoService conhecimentoService,
            ChunkingService chunkingService) {

        this.conhecimentoService = conhecimentoService;
        this.chunkingService = chunkingService;
    }

    @GetMapping
    public String obterConhecimento() {

        return conhecimentoService.obterConhecimento();
    }

    @GetMapping("/chunks")
    public List<String> obterChunks() {

        String texto = conhecimentoService.obterConhecimento();

        return chunkingService.dividir(texto);
    }
}