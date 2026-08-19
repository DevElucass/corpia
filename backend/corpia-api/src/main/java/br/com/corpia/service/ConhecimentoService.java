package br.com.corpia.service;

import org.springframework.stereotype.Service;

import br.com.corpia.util.ConhecimentoExtractor;

@Service
public class ConhecimentoService {

    private final ConhecimentoExtractor conhecimentoExtractor;

    public ConhecimentoService(
            ConhecimentoExtractor conhecimentoExtractor) {

        this.conhecimentoExtractor = conhecimentoExtractor;
    }

    public String obterConhecimento() {

        return conhecimentoExtractor.extrairTexto();
    }
}
