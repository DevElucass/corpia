package br.com.corpia.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurriculoParser {

    public static String extrairEmail(String texto) {

        Pattern pattern = Pattern.compile(
                "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");

        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group();
        }

        return "Email não encontrado";
    }

    public static String extrairTelefone(String texto) {

        Pattern pattern = Pattern.compile(
                "(\\+55\\s?)?(\\(?\\d{2}\\)?\\s?)?9?\\d{4}[-\\s]?\\d{4}");

        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group().trim();
        }

        return "Telefone não encontrado";
    }

    public static String extrairNome(String texto) {

        String[] linhas = texto.split("\\r?\\n");

        for (String linha : linhas) {

            linha = linha.trim();

            if (linha.length() > 5
                    && linha.length() < 60
                    && linha.equals(linha.toUpperCase())
                    && !linha.contains("@")
                    && !linha.matches(".*\\d.*")) {

                return linha;
            }
        }

        return "Nome não encontrado";
    }

    public static String extrairCompetencias(String texto) {

        StringBuilder competencias = new StringBuilder();

        String[] palavrasChave = {

                "Java",
                "Spring Boot",
                "Spring",
                "Python",
                "PostgreSQL",
                "SQL",
                "React",
                "Angular",
                "HTML",
                "CSS",
                "JavaScript",
                "Docker",
                "Git",
                "GitHub",
                "Excel",
                "Power BI",
                "SAP",
                "WMS",
                "Kanban",
                "Scrum",
                "Lean",
                "Supply Chain",
                "Logística",
                "Gestão de Estoque",
                "Gestão de Transportes"

        };

        for (String habilidade : palavrasChave) {

            if (texto.toLowerCase().contains(habilidade.toLowerCase())) {

                competencias.append(habilidade)
                        .append("\n");

            }

        }

        if (competencias.isEmpty()) {
            return "Nenhuma competência encontrada.";
        }

        return competencias.toString();

    }
}