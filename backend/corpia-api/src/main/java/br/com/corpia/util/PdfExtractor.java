package br.com.corpia.util;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfExtractor {

    public static String extrairTexto(String caminhoArquivo) {

        try (PDDocument documento = Loader.loadPDF(new File(caminhoArquivo))) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(documento);

        } catch (IOException e) {

            throw new RuntimeException("Erro ao ler PDF.", e);

        }

    }

}