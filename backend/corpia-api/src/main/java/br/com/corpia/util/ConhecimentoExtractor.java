package br.com.corpia.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

@Component
public class ConhecimentoExtractor {

    private static final String CAMINHO_DOCUMENTO =
            "documentos/conhecimento/perfil_profissional.txt";

    public String extrairTexto() {

        try {
            Path caminho = Path.of(CAMINHO_DOCUMENTO);

            if (!Files.exists(caminho)) {
                throw new RuntimeException(
                        "Documento de conhecimento não encontrado: "
                                + CAMINHO_DOCUMENTO);
            }

            return Files.readString(
                    caminho,
                    StandardCharsets.UTF_8);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao ler o documento de conhecimento.",
                    e);
        }
    }
}