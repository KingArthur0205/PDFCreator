package com.gearset;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * A class for parsing text input and creating a PDF document.
 */
public class PDFParser {
    private final String inputFile; // Path to the input file
    private final String outputFile; // Path to the output PDF file
    private Document document; // iText Document instance for PDF creation

    /**
     * Constructor for the PDFParser.
     * Initializes the parser with input and output file paths.
     *
     * @param input  Path to the input file.
     * @param output Path to the output PDF file.
     */
    public PDFParser(String input, String output) {
        this.inputFile = input;
        this.outputFile = output;
    }

    /**
     * Creates a PDF document based on the content of the input file.
     * This method sets up the PDF document and invokes the parsing of the input file.
     */
    public void createPDF() {
        try {
            PdfWriter writer = new PdfWriter(outputFile); // Create a PdfWriter for the output file
            PdfDocument pdfDoc = new PdfDocument(writer); // Create a PdfDocument
            this.document = new Document(pdfDoc); // Initialize the Document object
            parseInputFile(); // Parse the input file and fill the PDF
            document.close(); // Close the document after parsing
        } catch (IOException e) {
            System.err.println("PDF creation failed."); // Log error message
            e.printStackTrace(); // Print stack trace for debugging
            System.exit(1); // Exit the program with an error code
        }
    }

    /**
     * Parses the input file and adds its content to the PDF document.
     * This method reads the input file line by line and processes it to create formatted paragraphs.
     */
    private void parseInputFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            ParagraphFormatter formatter = new ParagraphFormatter(document); // Initialize the ParagraphFormatter
            Stack<String> lineStack = new Stack<>(); // Stack to hold lines of text

            String line = br.readLine(); // Read the first line
            while (line != null) {
                // Read and stack all formatting commands and text lines
                while (line != null && line.startsWith(".")) {
                    lineStack.push(line); // Stack command lines
                    line = br.readLine(); // Read next line
                }
                while (line != null && !line.startsWith(".")) {
                    lineStack.push(line); // Stack text lines
                    line = br.readLine(); // Read next line
                }
                formatter.processParagraph(lineStack); // Process the stacked lines
                lineStack.clear(); // Clear the stack for the next paragraph
            }
            formatter.finishParagraph(); // Finalize the paragraph processing
        } catch (IOException e) {
            // Handle IOException if reading from the input file fails
        }
    }
}