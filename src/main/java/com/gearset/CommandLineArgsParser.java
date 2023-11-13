package com.gearset;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

/**
 * A class for parsing command line arguments for input and output file paths.
 */
public class CommandLineArgsParser {
    private final String[] args; // Array of command line arguments
    private final String inputFilePath; // Input file path
    private final String outputFilePath; // Output file path

    /**
     * Constructor for CommandLineArgsParser.
     * Initializes the parser with command line arguments and sets up the file paths.
     *
     * @param args Command line arguments.
     */
    public CommandLineArgsParser(String[] args) {
        this.args = args;
        inputFilePath = args.length >= 1 ? args[0] : ""; // Set inputFilePath to the first arg or an empty string if no args
        outputFilePath = args.length >= 2 ? args[1] : "./output.pdf"; // Set outputFilePath to the second arg or a default path
    }

    /**
     * Checks if the command line arguments are valid.
     * Validation includes checking the number of arguments and path validity.
     *
     * @return true if arguments are valid, false otherwise.
     */
    public boolean isArgsValid() {
        if (args.length < 1) {
            return false; // Return false if no arguments are provided
        }

        try {
            // Try parsing the paths to verify their validity
            Paths.get(inputFilePath);
            Paths.get(outputFilePath);
        } catch (InvalidPathException | NullPointerException ex) {
            System.err.println("Read path failed");
            return false;
        }
        return true; // Return true if no exception is thrown
    }

    /**
     * Checks if the input file exists.
     *
     * @return true if the input file exists, false otherwise.
     */
    public boolean ifFileExist() {
        try {
            File inputFile = new File(inputFilePath);
            return inputFile.exists(); // Return true if the input file exists
        } catch(NullPointerException ex) {
            return false; // Return false if a NullPointerException is caught
        }
    }

    /**
     * Checks if there is enough privilege to read the input file.
     *
     * @return true if the input file can be read, false otherwise.
     */
    public boolean ifEnoughPrivilegeForRead() {
        File inputFile = new File(inputFilePath);
        return inputFile.canRead(); // Return true if the input file is readable
    }

    /**
     * Checks if there is enough privilege to write to the output file.
     *
     * @return true if the output file can be written to, false otherwise.
     */
    public boolean ifEnoughPrivilegeForWrite() {
        File outputFile = new File(outputFilePath);
        return outputFile.canWrite(); // Return true if the output file is writable
    }

    /**
     * Gets the input file path.
     *
     * @return The input file path.
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * Gets the output file path.
     *
     * @return The output file path.
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }
}