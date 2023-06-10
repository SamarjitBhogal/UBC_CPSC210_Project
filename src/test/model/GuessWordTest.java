package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuessWordTest {

    GuessWord testGuessWord;
    LetterInput testInput;

    @BeforeEach
    void setUp() {
        testGuessWord = new GuessWord("Cat");
        testInput = new LetterInput("c");
    }

    @Test
    void testConstructor() {
        assertEquals("Cat", testGuessWord.getWord());
        assertEquals(3, testGuessWord.getCharacterLengthCurrentWord());
        assertEquals("___", testGuessWord.getBlankStrWord());
    }

    @Test
    void changeWordToGuessTest() {
        testGuessWord.changeWordToGuess("Icecream");
        assertEquals("Icecream", testGuessWord.getWord());
        testGuessWord.changeWordToGuess("Frog");
        assertEquals("Frog", testGuessWord.getWord());
    }

    @Test
    void changeWordUpdatesCharacterLength() {
        testGuessWord.changeWordToGuess("Jump");
        assertEquals(4, testGuessWord.getCharacterLengthCurrentWord());
        testGuessWord.changeWordToGuess("Application");
        assertEquals(11, testGuessWord.getCharacterLengthCurrentWord());
    }

    @Test
    void changeWordUpdatesBlankWord() {
        testGuessWord.changeWordToGuess("Frog");
        assertEquals("____", testGuessWord.getBlankStrWord());
        testGuessWord.changeWordToGuess("Application");
        assertEquals("___________", testGuessWord.getBlankStrWord());
    }

    @Test
    void skipWordTest() {
        testGuessWord.skipWordToGuess("Frog");
        assertEquals("____", testGuessWord.getBlankStrWord());
        assertEquals(4, testGuessWord.getCharacterLengthCurrentWord());
        assertEquals("Frog", testGuessWord.getWord());
    }

    @Test
    void makeBlankStrWordTest() {
        assertEquals("____", testGuessWord.makeBlankStrWord("Yoga"));
        assertEquals("_________", testGuessWord.makeBlankStrWord("Interface"));
    }

    @Test
    void makeBankWordTest() {
        LetterInput blank = new LetterInput("_");
        List<LetterInput> blankWord1 = new ArrayList<>();

        blankWord1.add(blank);
        blankWord1.add(blank);
        blankWord1.add(blank);
        blankWord1.add(blank);
        assertEquals(blankWord1.size(), testGuessWord.makeBlankWord("Yoga").size());

        blankWord1.add(blank);
        blankWord1.add(blank);
        blankWord1.add(blank);
        blankWord1.add(blank);
        blankWord1.add(blank);
        assertEquals(blankWord1.size(), testGuessWord.makeBlankWord("Interface").size());
    }

    @Test
    void constructBlankWordTest() {
        assertEquals("___", testGuessWord.constructBlankWordStr());
    }

    @Test
    void constructPartialWordTest() {
        testGuessWord.addLetter(testInput);

        assertEquals("C__", testGuessWord.constructBlankWordStr());
    }

    @Test
    void constructFinishedWordTest() {
        LetterInput t = new LetterInput("t");
        LetterInput a = new LetterInput("a");
        testGuessWord.addLetter(testInput);
        testGuessWord.addLetter(t);
        testGuessWord.addLetter(a);

        assertEquals("Cat", testGuessWord.constructBlankWordStr());
    }

    @Test
    void constructUpdatesBlankStrTest() {
        LetterInput t = new LetterInput("t");
        testGuessWord.addLetter(testInput);
        testGuessWord.addLetter(t);
        testGuessWord.constructBlankWordStr();

        assertEquals("C_t", testGuessWord.getBlankStrWord());
    }

    @Test
    void addWrongLetterTest() {
        testInput.setLetterInput("s");
        testGuessWord.addLetter(testInput);
        assertEquals("___", testGuessWord.getBlankStrWord());

        testInput.setCapital();
        testGuessWord.addLetter(testInput);
        assertEquals("___", testGuessWord.getBlankStrWord());
    }

    @Test
    void addInvalidInputsTest() {
        testInput.setLetterInput("#");
        testGuessWord.addLetter(testInput);
        assertEquals("___", testGuessWord.getBlankStrWord());

        testInput.setLetterInput(" ");
        testGuessWord.addLetter(testInput);
        assertEquals("___", testGuessWord.getBlankStrWord());

        testInput.setLetterInput("");
        testGuessWord.addLetter(testInput);
        assertEquals("___", testGuessWord.getBlankStrWord());
    }

    @Test
    void addValidLetterCheckTest() {
        testInput.setLetterInput("c");
        testGuessWord.addLetter(testInput);
        assertEquals("C__", testGuessWord.getBlankStrWord());

        LetterInput t = new LetterInput("t");
        testGuessWord.addLetter(t);
        assertEquals("C_t", testGuessWord.getBlankStrWord());

        LetterInput a = new LetterInput("a");
        testGuessWord.addLetter(a);
        assertEquals("Cat", testGuessWord.getBlankStrWord());
    }

    @Test
    void addCapitalTest() {
        testInput.setCapital();
        testGuessWord.addLetter(testInput);
        assertEquals("C__", testGuessWord.getBlankStrWord());

        LetterInput T = new LetterInput("T");
        testGuessWord.addLetter(T);
        assertEquals("C_t", testGuessWord.getBlankStrWord());

        LetterInput A = new LetterInput("A");
        testGuessWord.addLetter(A);
        assertEquals("Cat", testGuessWord.getBlankStrWord());
    }

    @Test
    void addLowerCaseTest() {
        testGuessWord.addLetter(testInput);
        assertEquals("C__", testGuessWord.getBlankStrWord());

        LetterInput t = new LetterInput("t");
        testGuessWord.addLetter(t);
        assertEquals("C_t", testGuessWord.getBlankStrWord());

        LetterInput a = new LetterInput("a");
        testGuessWord.addLetter(a);
        assertEquals("Cat", testGuessWord.getBlankStrWord());
    }

    @Test
    void addFirstLetterTest() {
        testGuessWord.addLetter(testInput);
        assertEquals("C__", testGuessWord.getBlankStrWord());
    }

    @Test
    void addLastLetterTest() {
        testInput.setLetterInput("t");
        testGuessWord.addLetter(testInput);
        assertEquals("__t", testGuessWord.getBlankStrWord());
    }

    @Test
    void addMiddleLetterTest() {
        testInput.setLetterInput("a");
        testGuessWord.addLetter(testInput);
        assertEquals("_a_", testGuessWord.getBlankStrWord());
    }

    @Test
    void addAllLettersTest() {
        testGuessWord.addLetter(testInput);
        LetterInput a = new LetterInput("a");
        testGuessWord.addLetter(a);
        LetterInput t = new LetterInput("t");
        testGuessWord.addLetter(t);

        assertEquals("Cat", testGuessWord.getBlankStrWord());
    }

    @Test
    void addMultipleLettersTest() {
        testGuessWord.changeWordToGuess("Hopping");
        LetterInput p = new LetterInput("p");
        testGuessWord.addLetter(p);
        assertEquals("__pp___", testGuessWord.getBlankStrWord());

        LetterInput h = new LetterInput("h");
        testGuessWord.addLetter(h);
        assertEquals("H_pp___", testGuessWord.getBlankStrWord());
    }

    @Test
    void findIndWrongLetterTest() {
        testInput.setLetterInput("h");
        List<Integer> indexes = testGuessWord.findIndexes(testInput);

        assertEquals(0, indexes.size());
    }

    @Test
    void findIndRightLetterTest() {
        testInput.setLetterInput("c");
        List<Integer> indexes = testGuessWord.findIndexes(testInput);

        assertEquals(1, indexes.size());
    }

    @Test
    void findIndMultipleRightLettersTest() {
        testGuessWord.changeWordToGuess("Hopping");
        testInput.setLetterInput("p");
        List<Integer> indexes1 = testGuessWord.findIndexes(testInput);
        assertEquals(2, indexes1.size());

        testGuessWord.changeWordToGuess("Maintain");
        testInput.setLetterInput("n");
        List<Integer> indexes2 = testGuessWord.findIndexes(testInput);
        assertEquals(2, indexes2.size());
    }

    @Test
    void addInputToHistoryTest() {
        testGuessWord.addLetter(testInput);
        assertEquals(1, testGuessWord.getLetterInputHistory().size());
    }

    @Test
    void addMultipleInputsToHistoryTest() {
        testGuessWord.addLetter(testInput);
        testGuessWord.addLetter(new LetterInput("a"));
        assertEquals(2, testGuessWord.getLetterInputHistory().size());
    }

    @Test
    void clearInputHistoryTest() {
        testGuessWord.addLetter(testInput);
        testGuessWord.addLetter(new LetterInput("a"));
        testGuessWord.clearInputHistory();
        assertEquals(0, testGuessWord.getLetterInputHistory().size());
    }

    @Test
    void isBlankWordGuessCompletelyTest() {
        testGuessWord.addLetter(testInput);
        assertFalse(testGuessWord.isGuessedCompletely());

        testGuessWord.addLetter(new LetterInput("a"));
        testGuessWord.addLetter(new LetterInput("t"));
        assertTrue(testGuessWord.isGuessedCompletely());
    }

    @Test
    void makeJsonTest() {
        String guessWord = testGuessWord.getWord();
        String blankWordStr = testGuessWord.getBlankStrWord();
        int characterLength = testGuessWord.getCharacterLengthCurrentWord();

        assertEquals(guessWord, testGuessWord.makeJsonObject().get("guessWord"));
        assertEquals(blankWordStr, testGuessWord.makeJsonObject().get("blankWordStr"));
        assertEquals(characterLength, testGuessWord.makeJsonObject().get("characterLength"));
        assertEquals(testGuessWord.getWordBank().size(),
                testGuessWord.makeJsonObject().getJSONArray("wordBank").toList().size());
        assertEquals(testGuessWord.getBlankWord().size(),
                testGuessWord.makeJsonObject().getJSONArray("blankWord").toList().size());
        assertEquals(testGuessWord.getLetterInputHistory().size(),
                testGuessWord.makeJsonObject().getJSONArray("letterInputHistory").toList().size());
    }

    @Test
    void makeLetterInputHistoryJsonTest() {
        testGuessWord.addLetter(testInput);
        testGuessWord.addLetter(new LetterInput("a"));

        assertEquals(testGuessWord.getLetterInputHistory().size(),
                testGuessWord.makeJsonObject().getJSONArray("letterInputHistory").toList().size());
    }

    @Test
    void getNextWordTest() {
        assertEquals("Math", testGuessWord.getNextWord());
        assertEquals("Elephant", testGuessWord.getNextWord());
        assertEquals("Cat", testGuessWord.getNextWord());
    }

    @Test
    void setBlankStrWordBasicTest() {
        testGuessWord.setBlankStrWord("____");
        assertEquals("____", testGuessWord.getBlankStrWord());

        testGuessWord.setBlankStrWord("______");
        assertEquals("______", testGuessWord.getBlankStrWord());
    }

    @Test
    void setBlankStrWordFilledTest() {
        testGuessWord.setBlankStrWord("_a_h");
        assertEquals("_a_h", testGuessWord.getBlankStrWord());

        testGuessWord.setBlankStrWord("_aa__h__hj");
        assertEquals("_aa__h__hj", testGuessWord.getBlankStrWord());
    }

    @Test
    void setBlankWordTest() {
        testGuessWord.setBlankWord("King");
        assertEquals(4, testGuessWord.getBlankWord().size());

        testGuessWord.setBlankWord("Keyboard");
        assertEquals(8, testGuessWord.getBlankWord().size());
    }

    @Test
    void setBlankWordListTest() {
        List<LetterInput> test1 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LetterInput input = new LetterInput("_");
            test1.add(input);
        }
        testGuessWord.setBlankWord(test1);
        assertEquals(3, testGuessWord.getBlankWord().size());
        test1.clear();

        for (int i = 0; i < 6; i++) {
            LetterInput input = new LetterInput("_");
            test1.add(input);
        }
        testGuessWord.setBlankWord(test1);
        assertEquals(6, testGuessWord.getBlankWord().size());
    }

    @Test
    void setCharacterLength() {
        testGuessWord.setCharacterLength(6);
        assertEquals(6, testGuessWord.getCharacterLengthField());

        testGuessWord.setCharacterLength(20);
        assertEquals(20, testGuessWord.getCharacterLengthField());
    }

    @Test
    void setWordBankTest() {
        String[] words1 = {"Math", "Elephant", "Cat", "Computer"};
        String[] words2 = {"Math", "Elephant", "Cat", "Computer", "King"};
        List<String> wordBank = new ArrayList<>(Arrays.asList(words1));

        testGuessWord.setWordBank(wordBank);
        assertEquals(wordBank, testGuessWord.getWordBank());

        wordBank = new ArrayList<>(Arrays.asList(words2));
        testGuessWord.setWordBank(wordBank);
        assertEquals(wordBank, testGuessWord.getWordBank());
    }

    @Test
    void setLetterInputHistoryTest() {
        List<LetterInput> newHistory = new ArrayList<>();
        newHistory.add(new LetterInput("a"));
        newHistory.add(new LetterInput("t"));

        testGuessWord.setLetterInputHistory(newHistory);
        assertEquals(newHistory, testGuessWord.getLetterInputHistory());
    }
}
