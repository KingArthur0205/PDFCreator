package com.gearset;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.itextpdf.layout.Document;

import java.io.IOException;
import java.util.Stack;
public class TestParagraphFormatter {
    private ParagraphFormatter paragraphFormatter;
    private Document testDocument;

    @BeforeEach
    public void setUp() throws IOException {
        PdfWriter writer = new PdfWriter("./testFile.pdf"); // Create a PdfWriter for the output file
        PdfDocument pdfDoc = new PdfDocument(writer); // Create a PdfDocument
        testDocument = new Document(pdfDoc); // Initialize the Document object
        paragraphFormatter = new ParagraphFormatter(testDocument);
    }

    @Test
    public void testProcessParagraphWithCommands() throws IOException {
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".fill");
        paragraphStack.push("Some text");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
    }
}
