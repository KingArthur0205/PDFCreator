package com.gearset;

/**
 * Main application class that processes command line arguments and performs PDF parsing.
 */
public class App 
{
    /**
     * The main method that acts as the entry point for the application.
     * It parses the command line arguments and performs necessary checks before proceeding
     * with the PDF creation process.
     *
     * @param args The command line arguments provided to the application.
     */
    public static void main( String[] args )
    {
        // Create a new CommandLineArgsParser object with the provided arguments
        CommandLineArgsParser argsParser = new CommandLineArgsParser(args);

        // Check if the arguments are valid (e.g., correct format, required arguments present)
        if (!argsParser.isArgsValid()) {
            // Inform the user about invalid arguments and terminate the program
            System.err.println("Please input valid paths for input and output file.");
            System.exit(1);
        }

        // Check if the input file exists
        if (!argsParser.ifFileExist()) {
            // Inform the user that the input file is missing and terminate the program
            System.err.println("The input file doesn't exist. Please check your input.");
            System.exit(1);
        }

        // Check if the application has enough privileges to read from and write to the specified files
        if (!argsParser.ifEnoughPrivilegeForRead()) {
            // Inform the user about insufficient file operation privileges and terminate the program
            System.err.println("You do not have enough privileges to operate on the input file.");
            System.exit(1);
        }

        // Create a PDFParser object with the input and output file paths provided in the arguments
        PDFParser creator = new PDFParser(argsParser.getInputFilePath(), argsParser.getOutputFilePath());
        // Initiate the PDF creation process
        creator.createPDF();
    }
}