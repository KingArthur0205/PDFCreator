package com.gearset;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;

public class ParagraphFormatter {
    private Document document;
    private Paragraph currentParagraph;
    private PdfFont normalFont;
    private PdfFont boldFont;
    private PdfFont italicFont;

    public ParagraphFormatter(Document document) throws IOException {
        this.document = document;
        this.currentParagraph = new Paragraph();
        this.normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        this.boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        this.italicFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    }

    public void processLine(String line) throws IOException {
        if (line.startsWith(".indent")) {
            handleIndent(line);
        } else if (line.startsWith(".paragraph")) {
            startNewParagraph();
        } else if (line.startsWith(".fill")) {
            currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
        } else if (line.startsWith(".nofill")) {
            currentParagraph.setTextAlignment(TextAlignment.LEFT);
        } else if (line.startsWith(".bold")) {
            currentParagraph.setFont(boldFont);
        } else if (line.startsWith(".italics")) {
            currentParagraph.setFont(italicFont);
        } else if (line.startsWith(".regular")) {
            currentParagraph.setFont(normalFont);
        } else if (line.startsWith(".large")) {
            currentParagraph.setFontSize(16);
        } else if (line.startsWith(".normal")) {
            currentParagraph.setFontSize(12);
        } else {
            // Handle the text
            addText(line);
        }
    }

    private void handleIndent(String line) {
        int indent = Integer.parseInt(line.split("\\s+")[1]);
        currentParagraph.setMarginLeft(indent * 20); // Assuming one indent unit is 20 points.
    }

    private void startNewParagraph() {
        if (currentParagraph != null && !currentParagraph.isEmpty()) {
            document.add(currentParagraph);
        }
        currentParagraph = new Paragraph();
    }

    private void addText(String text) {
        if (!text.trim().isEmpty()) {
            currentParagraph.add(new Text(text));
        }
    }

    public void finishParagraph() {
        if (currentParagraph != null && !currentParagraph.isEmpty()) {
            document.add(currentParagraph);
        }
    }
}
