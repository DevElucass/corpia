package br.com.corpia.controller;

import br.com.corpia.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

   @PostMapping("/upload")
public ResponseEntity<String> upload(
        @RequestParam("arquivo") MultipartFile arquivo) throws IOException {

    documentoService.salvarArquivo(arquivo);

    return ResponseEntity.ok("Arquivo recebido com sucesso!");
}
}