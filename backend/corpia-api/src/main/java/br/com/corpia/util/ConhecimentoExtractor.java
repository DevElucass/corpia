package br.com.corpia.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConhecimentoExtractor {

    private static final List<Path> CAMINHOS_DOCUMENTO = List.of(
            Path.of("documentos/conhecimento/perfil_profissional.txt"),
            Path.of("backend/corpia-api/documentos/conhecimento/perfil_profissional.txt"));

    public String extrairTexto() {
        try {
            Path caminho = CAMINHOS_DOCUMENTO.stream()
                    .filter(Files::exists)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Documento de conhecimento não encontrado."));

            return Files.readString(caminho, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o documento de conhecimento.", e);
        }
    }
}