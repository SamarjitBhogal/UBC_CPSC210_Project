package persistence;

import model.GuessWord;
import model.HangMan;
import model.LetterInput;
import model.Score;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer which writes a JSON representation of hangman to file
// (includes representations of guessword, letterinput, score, and hangman)
// Source: this class and methods are based on the JsonWriter class present in JsonSerializationDemo
//         https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private static final int INDENT = 4;
    private String destination;
    private PrintWriter writer;

    // EFFECTS: creates a writer with a destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if file is not found through PrintWriter
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of guessword to file
    public void writeGW(GuessWord gw) {
        JSONObject json = gw.makeJsonObject();
        saveOnFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of hangman to file
    public void writeHM(HangMan hm) {
        JSONObject json = hm.makeJsonObject();
        saveOnFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of letterinput to file
    public void writeLI(LetterInput li) {
        JSONObject json = li.makeJsonObject();
        saveOnFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of score to file
    public void writeS(Score s) {
        JSONObject json = s.makeJsonObject();
        saveOnFile(json.toString(INDENT));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the string to file
    private void saveOnFile(String json) {
        writer.print(json);
    }

    // getters and setters

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
