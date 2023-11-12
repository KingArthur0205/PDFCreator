package com.gearset;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PDFParser {
    private final String inputFile;
    private final String outputFile;
    private Document document;

    public PDFParser(String input, String output) {
        this.inputFile = input;
        this.outputFile = output;
    }

    public void createPDF() {
        try {
            PdfWriter writer = new PdfWriter(outputFile);
            PdfDocument pdfDoc = new PdfDocument(writer);
            this.document = new Document(pdfDoc);
            parseInputFile();
            document.close();
        } catch (IOException e) {
            System.err.println("PDF creation failed.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void parseInputFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            String line;
            ParagraphFormatter formatter = new ParagraphFormatter(document);

            while ((line = br.readLine()) != null) {
                formatter.processLine(line);
            }
            formatter.finishParagraph();
        } catch (IOException e) {

        }
    }
}
