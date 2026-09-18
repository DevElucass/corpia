package br.com.corpia.service;

import br.com.corpia.model.Documento;
import br.com.corpia.repository.DocumentoRepository;
import br.com.corpia.util.CurriculoParser;
import br.com.corpia.util.PdfExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private RagIndexService ragIndexService;

    private final Path pastaUploads = Paths.get("documentos/uploads");

    public Documento salvar(Documento documento) {

        documento.setDataUpload(LocalDateTime.now());

        return documentoRepository.save(documento);
    }

    public List<Documento> listarTodos() {

        return documentoRepository.findAll();
    }

    public String salvarArquivo(MultipartFile arquivo)
            throws IOException {

        if (arquivo.isEmpty()) {
            throw new IOException(
                    "Nenhum arquivo foi enviado.");
        }

        if (!Files.exists(pastaUploads)) {
            Files.createDirectories(pastaUploads);
        }

        String nomeOriginal = arquivo.getOriginalFilename();

        if (nomeOriginal == null
                || nomeOriginal.isBlank()) {

            throw new IOException(
                    "Nome do arquivo inválido.");
        }

        /*
         * Mantém somente o nome do arquivo,
         * removendo qualquer caminho enviado pelo cliente.
         */
        String nomeArquivo = Paths.get(nomeOriginal)
                .getFileName()
                .toString();

        Path destino = pastaUploads.resolve(nomeArquivo);

        arquivo.transferTo(destino);

        String texto = PdfExtractor.extrairTexto(
                destino.toString());

        System.out.println(
                "========== TEXTO EXTRAÍDO ==========");

        System.out.println(texto);

        String nome = CurriculoParser.extrairNome(texto);

        String email = CurriculoParser.extrairEmail(texto);

        String telefone = CurriculoParser.extrairTelefone(texto);

        String competencias = CurriculoParser.extrairCompetencias(texto);

        System.out.println("NOME: " + nome);
        System.out.println("EMAIL: " + email);
        System.out.println("TELEFONE: " + telefone);

        System.out.println("COMPETÊNCIAS:");
        System.out.println(competencias);

        System.out.println("====================================");

        Documento documento = new Documento();

        documento.setNome(nome);
        documento.setTipo(arquivo.getContentType());
        documento.setCaminhoArquivo(
                destino.toString());
        documento.setConteudo(texto);
        documento.setDataUpload(
                LocalDateTime.now());

        Documento documentoSalvo = documentoRepository.save(documento);

        System.out.println(
                "Documento salvo no PostgreSQL. ID: "
                        + documentoSalvo.getId());

        int chunks = ragIndexService.indexarDocumento(
                texto,
                nomeArquivo);

        System.out.println(
                "Documento indexado no PgVector. Chunks: "
                        + chunks);

        return destino.toString();
    }
}