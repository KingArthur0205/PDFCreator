package com.gearset;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class ParagraphFormatter {
    private final Document document; // The iText Document object to which paragraphs are added
    private Paragraph currentParagraph; // The current Paragraph being constructed
    private PdfFont normalFont; // Normal text font
    private PdfFont boldFont; // Bold text font
    private PdfFont italicFont; // Italic text font
    private String fontStyle; // Current font style to be applied
    private int indentSpace = 0; // Accumulated indent space

    /**
     * Constructor for ParagraphFormatter.
     * Initializes the ParagraphFormatter with a given document and sets up fonts.
     *
     * @param document The document to which the paragraphs will be added.
     * @throws IOException If there is an issue creating the fonts.
     */
    public ParagraphFormatter(Document document) throws IOException {
        this.document = document;
        this.currentParagraph = new Paragraph();
        initializeFonts();
    }

    /**
     * Initializes the fonts used in the paragraphs.
     * This method sets up normal, bold, and italic fonts.
     *
     * @throws IOException If there is an issue creating the fonts.
     */
    private void initializeFonts() throws IOException {
        normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        italicFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);
    }

    /**
     * Processes a stack of strings to format and add paragraphs to the document.
     * This method interprets commands like ".paragraph", ".fill", etc.
     *
     * @param paragraphStack A stack of strings representing paragraph commands and content.
     * @throws IOException If there is an issue processing the paragraph.
     */
    public void processParagraph(Stack<String> paragraphStack) throws IOException {
        List<String> paragraphTexts = extractText(paragraphStack);
        String paragraphCommand;
        if (paragraphStack.contains(".paragraph"))
            startNewParagraph();

        while (!paragraphStack.isEmpty()) {
            paragraphCommand = paragraphStack.pop();
            switch(paragraphCommand) {
                case ".paragraph":
                    break;
                case ".fill":
                    currentParagraph.setTextAlignment(TextAlignment.JUSTIFIED);
                    break;
                case ".nofill":
                    currentParagraph.setTextAlignment(TextAlignment.LEFT);
                    break;
                default:
                    processParagraphCommand(paragraphCommand);
            }
        }
        addText(paragraphTexts);
    }


    /**
     * Processes individual paragraph commands.
     * This method handles specific formatting commands such as ".indent", ".bold", etc.
     *
     * @param command        The paragraph command.
     */
    private void processParagraphCommand(String command) {
        if (command.startsWith(".indent")) {
            handleIndent(command);
        } else if (command.startsWith(".bold") || command.startsWith(".italics") || command.startsWith(".regular")) {
            fontStyle = command;
        } else if (command.startsWith(".large")) {
            currentParagraph.setFontSize(16);
        } else if (command.startsWith(".normal")) {
            currentParagraph.setFontSize(12);
        }
    }

    /**
     * Extracts text lines from the paragraph stack.
     * This method removes and returns text lines from the stack until a command is encountered.
     *
     * @param paragraphStack The stack of paragraph strings.
     * @return A list of text strings to be added to the paragraph.
     */
    private List<String> extractText(Stack<String> paragraphStack) {
        LinkedList<String> textList = new LinkedList<>();
        String line;
        while (!(line = paragraphStack.peek()).startsWith(".")) {
            paragraphStack.pop();
            textList.addFirst(line);
        }
        return textList;
    }

    /**
     * Handles indentation for a paragraph.
     * This method sets the left margin for a paragraph based on the indentation level.
     *
     * @param line The line containing the indentation command.
     */

    private void handleIndent(String line) {
        int indent = Integer.parseInt(line.split("\\s+")[1]);
        indentSpace += indent * 20;
        currentParagraph.setMarginLeft(indentSpace); // Assuming one indent unit is 20 points.
        document.add(currentParagraph);
    }

    /**
     * Starts a new paragraph.
     * This method adds the current paragraph to the document (if not empty) and initializes a new paragraph.
     */
    private void startNewParagraph() {
        if (currentParagraph != null && !currentParagraph.isEmpty()) {
            document.add(currentParagraph);
        }
        currentParagraph = new Paragraph();
    }

    /**
     * Adds text to the current paragraph.
     *
     * @param strLines The text to add.
     */
    private void addText(List<String> strLines) {
        for (String textStr : strLines) {
            textStr = prependSpaceIfNotPunctuation(textStr);
            if (!textStr.trim().isEmpty()) {
                Text text = new Text(textStr);
                setFontStyle(text);
                currentParagraph.add(text);
            }
        }
    }

    /**
     * Prepends a space to the text if it does not start with punctuation.
     * This method ensures that there is a space before a word unless it starts with punctuation.
     *
     * @param text The text to process.
     * @return The processed text.
     */
    private String prependSpaceIfNotPunctuation(String text) {
        return Pattern.matches("\\p{Punct}", text.substring(0, 1)) ? text : " " + text;
    }

    /**
     * Sets the font style of the given text.
     * This method applies the current font style to the text.
     *
     * @param text The text to which the font style will be applied.
     */
    private void setFontStyle(Text text) {
        if (fontStyle == null)
            return;
        switch (fontStyle) {
            case ".bold":
                text.setFont(boldFont);
                break;
            case ".italics":
                text.setFont(italicFont);
                break;
            case ".regular":
                text.setFont(normalFont);
                break;
        }
    }

    /**
     * Finalizes the current paragraph and adds it to the document.
     * This method adds the current paragraph to the document if it's not empty.
     */
    public void finishParagraph() {
        if (currentParagraph != null && !currentParagraph.isEmpty()) {
            document.add(currentParagraph);
        }
    }
}
