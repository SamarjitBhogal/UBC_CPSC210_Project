package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LetterInputTest {

    LetterInput testInput;

    @BeforeEach
    void setUp() {
        testInput = new LetterInput("c");
    }

    @Test
    void testConstructor() {
        assertEquals("c", testInput.getLetter());
    }

    @Test
    void isValidSingleTest() {
        assertTrue(testInput.isValid());
    }

    @Test
    void isValidMultipleTest() {
        testInput.setLetterInput("cc");
        assertFalse(testInput.isValid());
    }

    @Test
    void isValidSymbolTest() {
        testInput.setLetterInput("?");
        assertFalse(testInput.isValid());
        testInput.setLetterInput("@");
        assertFalse(testInput.isValid());
        testInput.setLetterInput("@@");
        assertFalse(testInput.isValid());
    }

    @Test
    void isValidEmptyTest() {
        testInput.setLetterInput("");
        assertFalse(testInput.isValid());
        testInput.setLetterInput(" ");
        assertFalse(testInput.isValid());
    }

    @Test
    void inputSetUpperCaseTest() {
        testInput.setCapital();
        assertEquals("C", testInput.getLetter());
        testInput.setCapital();
        assertEquals("C", testInput.getLetter());
    }

    @Test
    void inputSetLowerCaseTest() {
        testInput.setLetterInput("C");
        testInput.setLowerCase();
        assertEquals("c", testInput.getLetter());
        testInput.setLowerCase();
        assertEquals("c", testInput.getLetter());
    }

    @Test
    void makeJsonTest() {
        String input = testInput.getLetter();
        assertEquals(input, testInput.makeJsonObject().get("input"));
    }
}
