package persistence;

import model.GuessWord;
import model.HangMan;
import model.LetterInput;
import model.Score;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Source: Tests made in this test class were influenced by those present in JsonSerializationDemo repo
//         specifically JsonReaderTest:  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest {

    @Test
    void testConstructor() {
        JsonReader reader = new JsonReader("./data/testConstructor.json");

        assertEquals("./data/testConstructor.json", reader.getSource());
    }

    @Test
    void readerFileDoesNotExistTest() {
        JsonReader reader = new JsonReader("./data/doesNotExist.json");
        try {
            GuessWord gw = new GuessWord("Man");
            gw.changeWordToGuess("King");
            gw = reader.readGuessWord();
            fail("IO Exception was expected...");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void readerGuessWordTest() {
        JsonReader reader = new JsonReader("./data/readerGuessWordTest.json");
        try {
            GuessWord gw = new GuessWord("Man"); // "Man" is temp and needed
            gw.changeWordToGuess("Loop");
            gw = reader.readGuessWord();

            assertEquals("Loop", gw.getWord());
            assertEquals("____", gw.getBlankStrWord());
            assertEquals(4, gw.getCharacterLengthCurrentWord());
            assertEquals(4, gw.getBlankWord().size());
            assertEquals(0, gw.getLetterInputHistory().size());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerWordBankChangedTest () {
        JsonReader reader = new JsonReader("./data/readerWordBankChangedTest.json");
        try {
            GuessWord gw1 = new GuessWord("Man");
            GuessWord gw2 = new GuessWord("Jump");
            List<String> oldBank = gw2.getWordBank();

            gw1.changeWordToGuess(gw1.getNextWord()); // Math
            gw1.changeWordToGuess(gw1.getNextWord()); // Elephant
            gw1 = reader.readGuessWord();
            oldBank.remove(0);
            oldBank.remove(0);

            assertEquals(oldBank, gw1.getWordBank());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerLetterInputHistoryFilledTest() {
        JsonReader reader = new JsonReader("./data/readerLetterInputHistoryFilledTest.json");
        try {
            GuessWord gw = new GuessWord("Man"); // "Man" is temp and needed
            gw.changeWordToGuess("Loop");
            gw.addLetter(new LetterInput("l"));
            gw.addLetter(new LetterInput("o"));
            gw = reader.readGuessWord();

            assertEquals("Loop", gw.getWord());
            assertEquals("Loo_", gw.getBlankStrWord());
            assertEquals(4, gw.getCharacterLengthCurrentWord());
            assertEquals(4, gw.getBlankWord().size());
            assertEquals(2, gw.getLetterInputHistory().size());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerBasicHangManTest() {
        JsonReader reader = new JsonReader("./data/readerBasicHangManTest.json");
        try {
            HangMan hm = reader.readHangMan();

            assertEquals(0, hm.getCurrentStage());
            assertEquals(6, hm.getGuessesRemaining());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerAlteredHangManTest() {
        JsonReader reader = new JsonReader("./data/readerAlteredHangManTest.json");
        try {
            HangMan hm = new HangMan();

            hm.updateStageAndGuesses();
            hm.updateStageAndGuesses();
            hm = reader.readHangMan();

            assertEquals(2, hm.getCurrentStage());
            assertEquals(4, hm.getGuessesRemaining());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerLetterInputTest() {
        JsonReader reader = new JsonReader("./data/readerLetterInputTest.json");
        try {
            LetterInput li = new LetterInput("h");
            li.setLetterInput("k");
            li = reader.readLetterInput();

            assertEquals("k", li.getLetter());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerBasicScoreTest() {
        JsonReader reader = new JsonReader("./data/readerBasicScoreTest.json");
        try {
            Score s = reader.readScore();

            assertEquals(0, s.getScore());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void readerAlteredScoreTest() {
        JsonReader reader = new JsonReader("./data/readerAlteredScoreTest.json");
        try {
            Score s = new Score();

            s.increaseScore();
            s.increaseScore();
            s = reader.readScore();

            assertEquals(200, s.getScore());
        } catch (IOException e) {
            fail("Failed to read from file...");
        }
    }

    @Test
    void setSourceTest() {
        JsonReader reader = new JsonReader("./data/setSourceTest.json");
        reader.setSource("./data/newSourceFile.json");

        assertEquals("./data/newSourceFile.json", reader.getSource());
    }
}
