package br.com.corpia.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompetenciaService {

    private final List<String> competenciasConhecidas = List.of(
        "Excel",
        "SAP",
        "WMS",
        "Lean",
        "Supply Chain",
        "Logística",
        "Gestão de Estoque",
        "Gestão de Transportes"
);

    public List<String> identificarCompetencias(String texto) {

        List<String> encontradas = new ArrayList<>();

        if (texto == null || texto.isBlank()) {
            return encontradas;
        }

        String textoNormalizado = texto.toLowerCase();

        for (String competencia : competenciasConhecidas) {

            if (textoNormalizado.contains(competencia.toLowerCase())) {
                encontradas.add(competencia);
            }
        }

        return encontradas;
    }
}