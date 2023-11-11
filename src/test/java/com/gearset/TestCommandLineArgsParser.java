package com.gearset;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.annotation.Target;
import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TestCommandLineArgsParser {
    private CommandLineArgsParser parser;

    @Test
    void testNoArgs() {
        String[] args = {};
        parser = new CommandLineArgsParser(args);
        assertFalse(parser.isArgsValid());
    }

    // ------------------------- Input Argument Tests -------------------------
    @Test
    void testValidInputArg1() {
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
    void testExistInputFile() {
        String[] args = {"input.txt"};
        parser = new CommandLineArgsParser(args);
        assertTrue(parser.isArgsValid());
        assertTrue(parser.ifFileExist());
    }

    @Test
    void testNonExistInputFile() {
        String[] args = {"non-existent.something"};
        parser = new CommandLineArgsParser(args);
        assertTrue(parser.isArgsValid());
        assertFalse(parser.ifFileExist());
    }

    @Test
    void hi() {
        File file = new File(new String());
    }
}
