package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HangManTest {

    HangMan testHangMan;

    @BeforeEach
    void setUp() {
        testHangMan = new HangMan();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testHangMan.getCurrentStage());
        assertEquals(6, testHangMan.getGuessesRemaining());
    }

    @Test
    void updateStageIncreaseTest() {
        testHangMan.updateStageAndGuesses();
        assertEquals(1, testHangMan.getCurrentStage());
        testHangMan.updateStageAndGuesses();
        assertEquals(2, testHangMan.getCurrentStage());
    }

    @Test
    void updateStageDecreaseGuessesTest() {
        testHangMan.updateStageAndGuesses();
        assertEquals(5, testHangMan.getGuessesRemaining());
        testHangMan.updateStageAndGuesses();
        assertEquals(4, testHangMan.getGuessesRemaining());
    }

    @Test
    void updateGuessesTest() {
        testHangMan.updateGuesses();
        assertEquals(5, testHangMan.getGuessesRemaining());
        testHangMan.updateGuesses();
        assertEquals(4, testHangMan.getGuessesRemaining());
    }

    @Test
    void resetHangManStageTest() {
        testHangMan.resetHangMan();
        assertEquals(0, testHangMan.getCurrentStage());
    }

    @Test
    void resetHangManHigherStageTest() {
        testHangMan.updateStageAndGuesses();
        testHangMan.updateStageAndGuesses();
        testHangMan.resetHangMan();
        assertEquals(0, testHangMan.getCurrentStage());
    }

    @Test
    void resetHangManGuessesTest() {
        testHangMan.resetHangMan();
        assertEquals(6, testHangMan.getGuessesRemaining());
    }

    @Test
    void resetHangManLowerGuessesTest() {
        testHangMan.updateGuesses();
        testHangMan.updateGuesses();
        testHangMan.resetHangMan();
        assertEquals(6, testHangMan.getGuessesRemaining());
    }

    @Test
    void makeJsonTest() {
        int stage = testHangMan.getCurrentStage();
        int guesses = testHangMan.getGuessesRemaining();

        assertEquals(stage, testHangMan.makeJsonObject().get("stage"));
        assertEquals(guesses, testHangMan.makeJsonObject().get("guesses"));
    }

    @Test
    void setStageTest() {
        assertEquals(0, testHangMan.getCurrentStage());
        testHangMan.setStage(4);
        assertEquals(4, testHangMan.getCurrentStage());
        testHangMan.setStage(6);
        assertEquals(6, testHangMan.getCurrentStage());
    }

    @Test
    void setGuessesTest() {
        assertEquals(6, testHangMan.getGuessesRemaining());
        testHangMan.setGuesses(1);
        assertEquals(1, testHangMan.getGuessesRemaining());
        testHangMan.setGuesses(0);
        assertEquals(0, testHangMan.getGuessesRemaining());
    }
}
