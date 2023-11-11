package com.gearset;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class CommandLineArgsParser {
    private final String[] args;
    private final String inputFilePath;
    private final String outputFilePath;

    public CommandLineArgsParser(String[] args) {
        this.args = args;
        inputFilePath = args.length >= 1 ? args[0] : "";
        outputFilePath = args.length >= 2 ? args[1] : "./output.pdf";
    }

    public boolean isArgsValid() {
        if (args.length < 1) {
            return false;
        }

        try { // Try parsing the paths to verify their validity
            Paths.get(inputFilePath);
            Paths.get(outputFilePath);
        } catch (InvalidPathException | NullPointerException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean ifFileExist() {
        try {
            File inputFile = new File(inputFilePath);
            return inputFile.exists();
        } catch(NullPointerException ex) {
            return false;
        }
    }

    public boolean ifEnoughPrivilegeForRead() {
        File inputFile = new File(inputFilePath);
        return inputFile.canRead();
    }

    public boolean ifEnoughPrivilegeForWrite() {
        File outputFile = new File(outputFilePath);
        return outputFile.canWrite();
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }
}
