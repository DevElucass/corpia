package br.com.corpia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ChunkingService {

    public List<String> dividir(String texto) {

        List<String> chunks = new ArrayList<>();

        if (texto == null || texto.isBlank()) {
            return chunks;
        }

        // Normaliza as quebras de linha do Windows para Unix
        String textoNormalizado = texto
                .replace("\r\n", "\n")
                .replace("\r", "\n");

        /*
         * Identifica títulos de seção escritos em letras maiúsculas.
         * Cada título inicia um novo chunk.
         */
        String[] secoes = textoNormalizado.split(
                "(?m)(?=^[A-ZÁÉÍÓÚÃÕÇ ]{5,}$)");

        for (String secao : secoes) {

            String chunk = secao.trim();

            if (!chunk.isBlank()) {
                chunks.add(chunk);
            }
        }

        return chunks;
    }
}