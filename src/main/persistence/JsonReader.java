package persistence;

import model.GuessWord;
import model.HangMan;
import model.LetterInput;
import model.Score;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads guessword, hangman, letterinput, and score objects from JSON data
// Source: this class and methods are based on the JsonReader class present in JsonSerializationDemo
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads guessword from file and returns it, throws IOException
    //          if an error occurs from doing so
    public GuessWord readGuessWord() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGuessWord(jsonObject);
    }

    // EFFECTS: reads hangman from file and returns it, throws IOException
    //          if an error occurs from doing so
    public HangMan readHangMan() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHangMan(jsonObject);
    }

    // EFFECTS: reads letterinput from file and returns it, throws IOException
    //          if an error occurs from doing so
    public LetterInput readLetterInput() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLetterInput(jsonObject);
    }

    // EFFECTS: reads score from file and returns it, throws IOException
    //          if an error occurs from doing so
    public Score readScore() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseScore(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it, throws IOException
    //          if an error occurs from doing so
    // Source: this method is based open the readFile method present in JsonSerializationDemo JsonReader class
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private String readFile(String source) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }
        return builder.toString();
    }

    // EFFECTS: parses guessword from JSON object and returns it
    private GuessWord parseGuessWord(JSONObject jsonObject) {
        String word = jsonObject.getString("guessWord");
        String blankWordStr = jsonObject.getString("blankWordStr");
        int characterLength = jsonObject.getInt("characterLength");

        List<LetterInput> blankWord = getBlankWordFromArray(jsonObject);
        List<LetterInput> letterInputHistory = getLetterInputHistoryFromArray(jsonObject);
        List<Object> wordBankFetch = jsonObject.getJSONArray("wordBank").toList();
        List<String> wordBank = new ArrayList<>();

        for (Object o: wordBankFetch) {
            wordBank.add(o.toString());
        }

        GuessWord gw = new GuessWord(word);
        gw.setBlankStrWord(blankWordStr);
        gw.setBlankWord(blankWord);
        gw.setCharacterLength(characterLength);
        gw.setWordBank(wordBank);
        gw.setLetterInputHistory(letterInputHistory);

        return gw;
    }

    // EFFECTS: returns the blankword from the jsonObject as a list of LetterInput
    // Source: This code is based on the one presented in JsonSerializationDemo JsonReader class:
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private List<LetterInput> getBlankWordFromArray(JSONObject jsonObject) {
        JSONArray blankWordArray = jsonObject.getJSONArray("blankWord");
        List<LetterInput> blankWord = new ArrayList<>();

        for (Object o: blankWordArray) {
            JSONObject letterInput = (JSONObject) o;
            blankWord.add(addLetterInput(letterInput));
        }
        return blankWord;
    }

    // EFFECTS: returns the letterInputHistory from the jsonObject as a list of LetterInput
    // Source: This code is based on the one presented in JsonSerializationDemo JsonReader class:
    //         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private List<LetterInput> getLetterInputHistoryFromArray(JSONObject jsonObject) {
        JSONArray letterInputHistoryArray = jsonObject.getJSONArray("letterInputHistory");
        List<LetterInput> letterInputHistory = new ArrayList<>();

        for (Object o: letterInputHistoryArray) {
            JSONObject letterInput = (JSONObject) o;
            letterInputHistory.add(addLetterInput(letterInput));
        }
        return letterInputHistory;
    }

    // EFFECTS: returns a letterInput from jsonObject
    private LetterInput addLetterInput(JSONObject jsonObject) {
        String input = jsonObject.getString("input");

        return new LetterInput(input);
    }

    // EFFECTS: parses hangman from JSON object and returns it
    private HangMan parseHangMan(JSONObject jsonObject) {
        int stage = jsonObject.getInt("stage");
        int guesses = jsonObject.getInt("guesses");
        HangMan hm = new HangMan();
        hm.setGuesses(guesses);
        hm.setStage(stage);

        return hm;
    }

    // EFFECTS: parses letterinput from JSON object and returns it
    private LetterInput parseLetterInput(JSONObject jsonObject) {
        String input = jsonObject.getString("input");

        return new LetterInput(input);
    }

    // EFFECTS: parses score from JSON object and returns it
    private Score parseScore(JSONObject jsonObject) {
        int score = jsonObject.getInt("score");
        Score s = new Score();
        s.setScore(score);

        return s;
    }

    // getters and setters

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
