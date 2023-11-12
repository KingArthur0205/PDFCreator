package com.gearset;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

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
            ParagraphFormatter formatter = new ParagraphFormatter(document);
            Stack<String> lineStack = new Stack<>();

            String line = br.readLine();
            while (line != null) {
                // Read all the commands
                while (line != null && line.startsWith(".")) {
                    lineStack.push(line);
                    line = br.readLine();
                }
                while (line != null && !line.startsWith(".")) {
                    lineStack.push(line);
                    line = br.readLine();
                }
                formatter.processParagraph(lineStack);
                lineStack.clear();
            }
            formatter.finishParagraph();
        } catch (IOException e) {

        }
    }
}
