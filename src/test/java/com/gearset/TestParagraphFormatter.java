package com.gearset;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
public class TestParagraphFormatter {
    private ParagraphFormatter paragraphFormatter;
    private Document testDocument;
    private static final String testFileDir = "./testFiles/";

    @BeforeAll
    public static void createTestDir() {
        try {
            new File(testFileDir).mkdirs();
        } catch (Exception e) {
            System.err.println("Failed to create" + testFileDir);
        }

    }

    private void setUp(int testFileNo) throws IOException {
        PdfWriter writer = new PdfWriter(testFileDir+ "testFile" + testFileNo + ".pdf"); // Create a PdfWriter for the output file
        PdfDocument pdfDoc = new PdfDocument(writer); // Create a PdfDocument
        testDocument = new Document(pdfDoc); // Initialize the Document object
        paragraphFormatter = new ParagraphFormatter(testDocument);
    }

    @Test
    public void testProcessParagraphWithFill() throws IOException {
        setUp(0);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".fill");
        paragraphStack.push("Some tests that are used to test the file generated. Open the file to see the result. This file contains the result for .fill.");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }

    @Test
    public void testProcessParagraphWithBold() throws IOException {
        setUp(1);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".bold");
        paragraphStack.push("Some tests that are used to test the file generated. Open the file to see the result");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }

    @Test
    public void testProcessParagraphWithItalic() throws IOException {
        setUp(2);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".italics");
        paragraphStack.push("Some tests that are used to test the file generated. Open the file to see the result");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }

    @Test
    public void testProcessParagraphWithRegular() throws IOException {
        setUp(3);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".regular");
        paragraphStack.push("Some tests that are used to test the file generated. Open the file to see the result");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }

    @Test
    public void testProcessParagraphWithLarge() throws IOException {
        setUp(4);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".large");
        paragraphStack.push("Some tests that are used to test the file generated. Open the file to see the result");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }

    @Test
    public void testProcessParagraphWithNormal() throws IOException {
        setUp(4);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".normal");
        paragraphStack.push("Some tests that are used to test the file generated. Open the file to see the result");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }

    @Test
    public void testProcessParagraphWithNewParagrahs() throws IOException {
        setUp(5);
        Stack<String> paragraphStack = new Stack<>();
        paragraphStack.push(".paragraph");
        paragraphStack.push(".normal");
        paragraphStack.push("Paragraph1");
        paragraphFormatter.processParagraph(paragraphStack);

        paragraphStack.push(".paragraph");
        paragraphStack.push(".normal");
        paragraphStack.push("Paragraph2");

        paragraphFormatter.processParagraph(paragraphStack);
        paragraphFormatter.finishParagraph();
        testDocument.close();
    }
}
