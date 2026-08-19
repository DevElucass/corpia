package br.com.corpia.repository;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.corpia.service.RagIndexService;

@RestController
@RequestMapping("/rag")
public class RagIndexController {

    private final RagIndexService ragIndexService;

    public RagIndexController(RagIndexService ragIndexService) {
        this.ragIndexService = ragIndexService;
    }

    @PostMapping("/indexar")
    public String indexarConhecimento() {

        int quantidade = ragIndexService.indexarConhecimento();

        return "Conhecimento indexado com sucesso. "
                + "Chunks gerados: "
                + quantidade;
    }

    @GetMapping("/testar")
    public String testarBusca(
            @RequestParam String pergunta) {

        List<Document> documentos = ragIndexService.testarBusca(pergunta);

        if (documentos.isEmpty()) {
            return "Nenhum documento encontrado.";
        }

        StringBuilder resposta = new StringBuilder();

        resposta.append("Pergunta:\n")
                .append(pergunta)
                .append("\n\n");

        resposta.append("Documentos recuperados: ")
                .append(documentos.size())
                .append("\n\n");

        for (int i = 0; i < documentos.size(); i++) {

            Document documento = documentos.get(i);

            resposta.append("========== DOCUMENTO ")
                    .append(i + 1)
                    .append(" ==========\n");

            resposta.append("ID: ")
                    .append(documento.getId())
                    .append("\n");

            resposta.append("Metadata: ")
                    .append(documento.getMetadata())
                    .append("\n");

            resposta.append("Conteúdo:\n")
                    .append(documento.getText())
                    .append("\n\n");
        }

        return resposta.toString();
    }
}