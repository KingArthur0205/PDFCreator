package com.gearset;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CommandLineArgsParser argsParser = new CommandLineArgsParser(args);
        if (!argsParser.isArgsValid()) { // Missing file path or incorrect format
            System.err.println("Please input valid paths for input and output file.");
            System.exit(1);
        }

        if (!argsParser.ifFileExist()) { // Check if the input exists
            System.err.println("The input file doesn't exist. Please check your input.");
            System.exit(1);
        }

        if (!argsParser.ifEnoughPrivilegeForRead() || !argsParser.ifEnoughPrivilegeForWrite()) {
            System.err.println("You do not have enough privileges to operate on the file.");
            System.exit(1);
        }

        PDFParser creator = new PDFParser(argsParser.getInputFilePath(), argsParser.getOutputFilePath());
        creator.createPDF();
    }
}
