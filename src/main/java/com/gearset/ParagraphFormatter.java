package com.gearset;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ParagraphFormatter {
    private Document document;
    private Paragraph currentParagraph;
    private PdfFont normalFont;
    private PdfFont boldFont;
    private PdfFont italicFont;
    private String font;
    private boolean fill = false;


    public ParagraphFormatter(Document document) throws IOException {
        this.document = document;
        this.currentParagraph = new Paragraph();
        this.normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        this.boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        this.italicFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    }

    public void processParagraph(Stack<String> paragraphStack) throws IOException {
        List<String> paragraphTexts = extractText(paragraphStack);
        String paragraph;
        if (paragraphStack.contains(".paragraph"))
            startNewParagraph();
        while (!paragraphStack.isEmpty()) {
            paragraph = paragraphStack.pop();
            if (paragraph.equals(".paragraph"))
                continue;
            if (paragraph.startsWith(".indent")) {
                handleIndent(paragraph);
            } else if (paragraph.startsWith(".fill")) {
                currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
                //fill = true;
            } else if (paragraph.startsWith(".nofill")) {
                currentParagraph.setTextAlignment(TextAlignment.LEFT);
                //fill = false;
            } else if (paragraph.startsWith(".bold")) {
                font = ".bold";
            } else if (paragraph.startsWith(".italics")) {
                font = ".italics";
            } else if (paragraph.startsWith(".regular")) {
                font = ".regular";
            } else if (paragraph.startsWith(".large")) {
                currentParagraph.setFontSize(16);
            } else if (paragraph.startsWith(".normal")) {
                currentParagraph.setFontSize(12);
            }
        }
        addText(paragraphTexts);
    }

    private List<String> extractText(Stack<String> paragraphStack) {
        LinkedList<String> textList = new LinkedList<>();
        String line;
        while (!(line = paragraphStack.peek()).startsWith(".")) {
            paragraphStack.pop();
            textList.addFirst(line);
        }
        return textList;
    }

    private void handleIndent(String line) {
        int indent = Integer.parseInt(line.split("\\s+")[1]);
        currentParagraph.setMarginLeft(indent * -20); // Assuming one indent unit is 20 points.
        document.add(currentParagraph);
        currentParagraph = new Paragraph();
    }

    private void startNewParagraph() {
        if (currentParagraph != null && !currentParagraph.isEmpty()) {
            document.add(currentParagraph);
        }
        currentParagraph = new Paragraph();
    }

    private void addText(List<String> texts) {
        for (int i = 0; i < texts.size(); ++i) {
            String textStr = texts.get(i);
            if (!textStr.trim().isEmpty()) {
                Text text = new Text(textStr + " ");


                if (font != null) {
                    if (font.equals(".bold")) {
                        text.setFont(boldFont);
                    } else if (font.equals(".italics")) {
                        text.setFont(italicFont);
                    } else if (font.equals(".regular")) {
                        text.setFont(normalFont);
                    }
                }

                currentParagraph.add(text);
            }
        }
    }

    public void finishParagraph() {
        if (currentParagraph != null && !currentParagraph.isEmpty()) {
            document.add(currentParagraph);
        }
    }
}
