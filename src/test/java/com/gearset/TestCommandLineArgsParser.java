package com.gearset;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.annotation.Target;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static junit.framework.Assert.*;

public class TestCommandLineArgsParser {
    private CommandLineArgsParser parser;

    @Test
    public void testConstructorWithNoArgs() {
        String[] args = {};
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        assertEquals("", parser.getInputFilePath());
        assertEquals("./output.pdf", parser.getOutputFilePath());
    }

    @Test
    public void testConstructorWithValidArgs() {
        String[] args = {"input.txt", "output.txt"};
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        assertEquals("input.txt", parser.getInputFilePath());
        assertEquals("output.txt", parser.getOutputFilePath());
    }

    // ------------------------- Input Argument Tests -------------------------

    @Test
    void testConstructorWithValidInputArg1() {
        String[] args = {"./input.txt"};
        parser = new CommandLineArgsParser(args);
        assertTrue(parser.isArgsValid());
    }

    @Test
    void testValidInputArg2() {
        String[] args = {"./input:txt"};
        parser = new CommandLineArgsParser(args);
        assertTrue(parser.isArgsValid());
    }

    @Test
    void testValidInputArg3() { // Although "/" is a valid file name, it is not a valid file name and will be tested below.
        String[] args = {"/"};
        parser = new CommandLineArgsParser(args);
        assertTrue(parser.isArgsValid());
    }

    @Test
    void testNullAsINputArg() {
        String[] args = {null}; // NUL is the only invalid character for path on UNIX systems
        parser = new CommandLineArgsParser(args);
        assertFalse(parser.isArgsValid());
    }

    // ------------------------- Input File Tests -------------------------
    @Test
    public void testIfFileExistWithExistingFile() throws Exception {
        Path tempFile = Files.createTempFile(null, null);
        String[] args = {tempFile.toString()};
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        assertTrue(parser.ifFileExist());
        Files.delete(tempFile); // Clean up
    }

    @Test
    public void testIfFileExistWithNonExistingFile() {
        String[] args = {"nonexistingfile.txt"};
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        assertFalse(parser.ifFileExist());
    }

    @Test
    public void testIfEnoughPrivilegeForReadWithReadableFile() throws Exception {
        Path tempFile = Files.createTempFile(null, null);
        String[] args = {tempFile.toString()};
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        assertTrue(parser.ifEnoughPrivilegeForRead());
        Files.delete(tempFile); // Clean up
    }

    @Test
    public void testIfEnoughPrivilegeForWriteWithWritableFile() throws Exception {
        Path tempFile = Files.createTempFile(null, null);
        String[] args = {tempFile.toString(), tempFile.toString()};
        CommandLineArgsParser parser = new CommandLineArgsParser(args);
        assertTrue(parser.ifEnoughPrivilegeForWrite());
        Files.delete(tempFile); // Clean up
    }

}
