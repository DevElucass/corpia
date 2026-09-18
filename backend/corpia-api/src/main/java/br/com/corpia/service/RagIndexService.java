package br.com.corpia.service;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.stereotype.Service;

import br.com.corpia.util.CurriculoParser;
import br.com.corpia.util.PdfExtractor;

@Service
public class RagIndexService {

        private static final String CAMINHO_PDF = "documentos/uploads/Curr_Prof_Jun.pdf";

        private static final String NOME_ARQUIVO = "Curr_Prof_Jun.pdf";

        private final VectorStore vectorStore;

        public RagIndexService(VectorStore vectorStore) {
                this.vectorStore = vectorStore;
        }

        public int indexarConhecimento() {

                String texto = PdfExtractor.extrairTexto(CAMINHO_PDF);

                return indexarDocumento(
                                texto,
                                NOME_ARQUIVO);
        }

        public int indexarDocumento(
                        String texto,
                        String nomeArquivo) {

                if (texto == null || texto.isBlank()) {
                        return 0;
                }

                String nomeCandidato = CurriculoParser.extrairNome(texto);

                /*
                 * Remove os chunks anteriores do mesmo documento.
                 */
                Filter.Expression filtro = new Filter.Expression(
                                Filter.ExpressionType.EQ,
                                new Filter.Key("source"),
                                new Filter.Value(nomeArquivo));

                vectorStore.delete(filtro);

                Document documento = Document.builder()
                                .text(texto)
                                .metadata("source", nomeArquivo)
                                .metadata("candidate_name", nomeCandidato)
                                .build();

                TokenTextSplitter splitter = TokenTextSplitter.builder()
                                .build();

                List<Document> chunks = splitter.split(List.of(documento));

                if (chunks.isEmpty()) {
                        return 0;
                }

                List<Document> documentosFinais = chunks.stream()
                                .map(chunk -> Document.builder()
                                                .id(chunk.getId())
                                                .text(chunk.getText())
                                                .metadata(
                                                                "source",
                                                                nomeArquivo)
                                                .metadata(
                                                                "candidate_name",
                                                                nomeCandidato)
                                                .metadata(
                                                                "chunk_index",
                                                                String.valueOf(
                                                                                chunks.indexOf(chunk)))
                                                .metadata(
                                                                "total_chunks",
                                                                String.valueOf(
                                                                                chunks.size()))
                                                .build())
                                .toList();

                vectorStore.add(documentosFinais);

                return documentosFinais.size();
        }

        public List<Document> testarBusca(String pergunta) {

                if (pergunta == null || pergunta.isBlank()) {
                        return List.of();
                }

                SearchRequest searchRequest = SearchRequest.builder()
                                .query(pergunta)
                                .topK(10)
                                .similarityThreshold(0.10)
                                .build();

                return vectorStore.similaritySearch(
                                searchRequest);
        }
}