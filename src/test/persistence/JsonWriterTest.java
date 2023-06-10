package persistence;

import model.GuessWord;
import model.HangMan;
import model.LetterInput;
import model.Score;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Source: Tests made in this test class were influenced by those present in JsonSerializationDemo repo
//         specifically JsonWriterTest:  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest {

    @Test
    void testConstructor() {
        JsonWriter jsonWriter = new JsonWriter("./data/testConstructor.json");
        assertEquals("./data/testConstructor.json", jsonWriter.getDestination());
    }

    @Test
    void openInvalidFileTest() {
        try {
            GuessWord gw = new GuessWord("Van");
            JsonWriter writer = new JsonWriter("./data/my59\0nofilename.json");
            writer.open();
            fail("IO Exception was expected...");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void writeGuessWordTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeGuessWordTest.json");
            GuessWord gw = new GuessWord("Loop");
            jsonWriter.open();
            jsonWriter.writeGW(gw);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            gw = jsonReader.readGuessWord();

            assertEquals("Loop", gw.getWord());
            assertEquals("____", gw.getBlankStrWord());
            assertEquals(4, gw.getCharacterLengthCurrentWord());
            assertEquals(4, gw.getBlankWord().size());
            assertEquals(0, gw.getLetterInputHistory().size());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeWordBankChangedTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeWordBankChangedTest.json");
            GuessWord gw1 = new GuessWord("Dog");
            GuessWord gw2 = new GuessWord("Cat");
            List<String> oldBank = gw2.getWordBank();

            gw1.changeWordToGuess(gw1.getNextWord()); // Math
            gw1.changeWordToGuess(gw1.getNextWord()); // Elephant
            jsonWriter.open();
            jsonWriter.writeGW(gw1);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            gw1 = jsonReader.readGuessWord();
            oldBank.remove(0);
            oldBank.remove(0);

            assertEquals(oldBank, gw1.getWordBank());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeLetterInputFilledTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeLetterInputFilledTest.json");
            GuessWord gw = new GuessWord("Loop");
            gw.addLetter(new LetterInput("l"));
            gw.addLetter(new LetterInput("o"));
            jsonWriter.open();
            jsonWriter.writeGW(gw);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            gw = jsonReader.readGuessWord();

            assertEquals("Loop", gw.getWord());
            assertEquals("Loo_", gw.getBlankStrWord());
            assertEquals(4, gw.getCharacterLengthCurrentWord());
            assertEquals(4, gw.getBlankWord().size());
            assertEquals(2, gw.getLetterInputHistory().size());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeBasicHangManTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeBasicHangManTest.json");
            HangMan hm = new HangMan();
            jsonWriter.open();
            jsonWriter.writeHM(hm);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            hm = jsonReader.readHangMan();

            assertEquals(0, hm.getCurrentStage());
            assertEquals(6, hm.getGuessesRemaining());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeAlteredHangManTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeAlteredHangManTest.json");
            HangMan hm = new HangMan();
            hm.updateStageAndGuesses();
            hm.updateStageAndGuesses();
            jsonWriter.open();
            jsonWriter.writeHM(hm);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            hm = jsonReader.readHangMan();

            assertEquals(2, hm.getCurrentStage());
            assertEquals(4, hm.getGuessesRemaining());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeLetterInputTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeLetterInputTest.json");
            LetterInput li = new LetterInput("k");
            jsonWriter.open();
            jsonWriter.writeLI(li);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            li = jsonReader.readLetterInput();

            assertEquals("k", li.getLetter());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeBasicScoreTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeBasicScoreTest.json");
            Score s = new Score();
            jsonWriter.open();
            jsonWriter.writeS(s);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            s = jsonReader.readScore();

            assertEquals(0, s.getScore());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void writeAlteredScoreTest() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/writeAlteredScoreTest.json");
            Score s = new Score();
            s.increaseScore();
            s.increaseScore();
            jsonWriter.open();
            jsonWriter.writeS(s);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader(jsonWriter.getDestination());
            s = jsonReader.readScore();

            assertEquals(200, s.getScore());
        } catch (IOException e) {
            fail("This exception should not been thrown...");
        }
    }

    @Test
    void setDestinationTest() {
        JsonWriter jsonWriter = new JsonWriter("./data/setDestinationTest.json");
        jsonWriter.setDestination("./data/newDestination.json");

        assertEquals("./data/newDestination.json", jsonWriter.getDestination());
    }
}
