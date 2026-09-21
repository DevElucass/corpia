package br.com.corpia.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class ConhecimentoExtractor {

    private static final String RECURSO_CONHECIMENTO =
            "conhecimento/perfil_profissional.txt";

    public String extrairTexto() {
        ClassPathResource recurso = new ClassPathResource(RECURSO_CONHECIMENTO);

        try (InputStream entrada = recurso.getInputStream()) {
            return new String(entrada.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao ler o documento de conhecimento.", e);
        }
    }
}