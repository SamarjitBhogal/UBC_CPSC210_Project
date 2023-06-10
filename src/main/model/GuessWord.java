package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a word to be guessed having a word,
// a blank word version of that word in the form list of inputs,
// it's character length, a history list of added LetterInputs
// and with a whole word bank accessible.
public class GuessWord implements Writable {
    private String guessWord;
    private String blankWordStr;
    private int characterLength;
    private List<LetterInput> blankWord;
    private List<LetterInput> letterInputHistory;
    private List<String> wordBank;

    // Represents words for the word bank:
    private String[] words = {"Math", "Elephant", "Cat", "Computer", "Birthday", "Tree", "Frog",
            "Many", "Microphone", "Delay", "Extension", "Hypothesis", "Knight", "Hero", "Disappointed", "Love",
            "infinite", "Popular", "Practice", "Eggplant", "Tractor", "Broad", "Night", "Season", "Pepper",
            "District", "Midterm", "Exam", "Course", "Quest", "Console", "Laptop", "Remote", "Cup", "Speaker", "Grade",
            "Comment", "Word", "Gnome", "Jump", "Table", "Glass", "Keyboard", "Bench", "Grape", "Sacrifice", "Poster",
            "Window", "Treatment", "Chrome", "Paper", "Umbrella", "Floor", "Dust", "Army", "Pilot", "Save", "Trash"};

    // REQUIRES: initial word to have non-zero length
    // EFFECTS: creates a word to guess with an initial word, it's character length,
    //          a blank word version of that word in format "___" where each letter
    //          of the word is a "_", instantiates a word bank, and a list of letterinputs
    //          as a history of inputs
    public GuessWord(String word) {
        this.guessWord = word;
        this.blankWord = makeBlankWord(word);
        this.blankWordStr = makeBlankStrWord(word);
        this.characterLength = getCharacterLengthCurrentWord();
        this.wordBank = new ArrayList<>(Arrays.asList(words));
        this.letterInputHistory = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: changes the word to guess, updates the character length,
    //          and updates the blank word version of it
    public void changeWordToGuess(String word) {
        this.guessWord = word;
        this.characterLength = getCharacterLengthCurrentWord();
        this.blankWord = makeBlankWord(word);
        this.blankWordStr = makeBlankStrWord(word);
    }

    // MODIFIES: this
    // EFFECTS:  changes the word to guess by grabbing another word, updates the character length,
    //           updates the blank word version of it, and logging it as an event
    public void skipWordToGuess(String word) {
        String prevWord = getWord();

        this.guessWord = word;
        this.characterLength = getCharacterLengthCurrentWord();
        this.blankWord = makeBlankWord(word);
        this.blankWordStr = makeBlankStrWord(word);
        EventLog.getInstance().logEvent(new Event(
                "Word to guess was skipped to: \""
                        + getWord() + "\". Previous word was: \"" + prevWord + "\"."));
    }

    // MODIFIES: this
    // EFFECTS: makes a blank word version of the current word to guess
    //          in format "___" where each letter of the word is a "_"
    public String makeBlankStrWord(String word) {
        return word.replaceAll("[a-zA-Z]", "_");
    }

    // MODIFIES: this
    // EFFECTS: makes a blank word version of the current word to guess as a list of letter inputs
    //          in format ___ where each letter of the word is a _
    public List<LetterInput> makeBlankWord(String word) {
        int wordLength = word.length();
        List<LetterInput> blankWord = new ArrayList<>();

        for (int i = 0; i < wordLength; i++) {
            LetterInput blank = new LetterInput("_");
            blankWord.add(blank);
        }
        return blankWord;
    }

    // MODIFIES: this
    // EFFECTS: translates the current blankWord into a string format and updates blankWordStr
    public String constructBlankWordStr() {
        List<LetterInput> blankWord = this.blankWord;
        String constructed = "";

        for (LetterInput i : blankWord) {
            constructed = constructed.concat(i.getLetter());
        }
        this.blankWordStr = constructed;
        return this.blankWordStr;
    }

    // MODIFIES: this
    // EFFECTS: Inserts a letter input into the current blankWord if and only if it belongs there
    //          then updates the blankWordStr field. Add every letter called with this method to
    //          the letter history list. Every valid letter input is logged, if letter is correct,
    //          for the given word, it is logged.
    //          if the word was "Cat" this is correct: "C__" where "_" gets replaced
    public void addLetter(LetterInput input) {
        List<LetterInput> blankWord = this.blankWord;
        List<Integer> indexes = findIndexes(input);
        addInputToHistory(input);

        if (input.isValid()) {
            if (indexes.size() > 0) {
                for (Integer index : indexes) {
                    if (index == 1) {
                        blankWord.remove(0);
                        blankWord.add(0, input.setCapital());
                    } else {
                        LetterInput temp = new LetterInput(input.getLetter());
                        blankWord.remove(index - 1);
                        blankWord.add(index - 1, temp.setLowerCase());
                    }
                    EventLog.getInstance().logEvent(new Event(
                            "The LetterInput: \""
                                    + input.getLetter() + "\" was added to the current blankWord."));
                }
            }
            constructBlankWordStr();
            EventLog.getInstance().logEvent(new Event(
                    "The LetterInput: \"" + input.getLetter() + "\" was added to the previous input list."));
        }
    }

    // EFFECTS: finds and returns all the indexes at which the letter input is found in the current word to guess
    //          NOTE: 1 is the first letter of the word not 0 in this case
    public ArrayList<Integer> findIndexes(LetterInput input) {
        int i = 1;
        String letter = input.getLetter();
        ArrayList<Integer> indexes = new ArrayList<>();

        while (i <= this.characterLength) {
            if (this.guessWord.substring(i - 1, i).equalsIgnoreCase(letter)) {
                indexes.add(i);
            }
            i++;
        }
        return indexes;
    }

    // MODIFIES: this
    // EFFECTS: a helper method that adds the input the history list of inputs
    public void addInputToHistory(LetterInput input) {
        this.letterInputHistory.add(input);
    }

    // MODIFIES: this
    // EFFECTS: clears the current input history list
    public void clearInputHistory() {
        this.letterInputHistory.clear();
    }

    // EFFECTS: returns true is the current blank word equals the actual word, false otherwise
    public Boolean isGuessedCompletely() {
        return getWord().equalsIgnoreCase(constructBlankWordStr());
    }

    @Override
    // EFFECTS: returns this as a JSON object
    // Source: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    //         this method was based on the ones present in the Thingy and WorkRoom classes
    public JSONObject makeJsonObject() {
        JSONObject json = new JSONObject();
        json.put("guessWord", guessWord);
        json.put("blankWord", makeBlankWordJson());
        json.put("blankWordStr", blankWordStr);
        json.put("characterLength", characterLength);
        json.put("wordBank", makeWordBankJson());
        json.put("letterInputHistory", makeLetterInputHistoryJson());

        return json;
    }

    // EFFECTS: returns wordBank as a json array
    private JSONArray makeWordBankJson() {
        JSONArray wordBank = new JSONArray();

        for (String s : this.wordBank) {
            wordBank.put(s);
        }
        return wordBank;
    }

    // EFFECTS: returns blankWord as a json array
    private JSONArray makeBlankWordJson() {
        JSONArray blankWord = new JSONArray();

        for (LetterInput l : this.blankWord) {
            blankWord.put(l.makeJsonObject());
        }
        return blankWord;
    }

    // EFFECTS: returns letterInputHistory as a json array
    private JSONArray makeLetterInputHistoryJson() {
        JSONArray letterInputHistory = new JSONArray();

        for (LetterInput l : this.letterInputHistory) {
            letterInputHistory.put(l.makeJsonObject());
        }
        return letterInputHistory;
    }

    // getters

    // REQUIRES: word bank list to not be empty
    // EFFECTS: gets the next word from word bank list
    public String getNextWord() {
        return this.wordBank.remove(0);
    }

    // EFFECTS: gets current word to guess
    public String getWord() {
        return this.guessWord;
    }

    public String getBlankStrWord() {
        return this.blankWordStr;
    }

    public int getCharacterLengthCurrentWord() {
        return this.guessWord.length();
    }

    public int getCharacterLengthField() {
        return this.characterLength;
    }

    public List<String> getWordBank() {
        return this.wordBank;
    }

    public List<LetterInput> getBlankWord() {
        return this.blankWord;
    }

    public List<LetterInput> getLetterInputHistory() {
        return this.letterInputHistory;
    }

    // setters

    public void setBlankStrWord(String word) {
        this.blankWordStr = word;
    }

    // uses current guess word to set
    public void setBlankWord(String word) {
        this.blankWord = makeBlankWord(word);
    }

    // uses list parameter to set
    public void setBlankWord(List<LetterInput> blankWord) {
        this.blankWord = blankWord;
    }

    public void setWordBank(List<String> bank) {
        this.wordBank = bank;
    }

    public void setCharacterLength(int value) {
        this.characterLength = value;
    }

    public void setLetterInputHistory(List<LetterInput> letterInputHistory) {
        this.letterInputHistory = letterInputHistory;
    }
}
