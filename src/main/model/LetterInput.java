package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a valid letter input for the hangman game
// empty string, whitespace, and any symbols are not valid
public class LetterInput implements Writable {
    private String input;

    // EFFECTS: creates a letter input
    public LetterInput(String letter) {
        this.input = letter;
    }

    // EFFECTS: returns true if the letter input has 1 character, false otherwise
    public Boolean isValid() {
        return this.input.length() == 1 && this.input.matches("[a-zA-Z]");
    }

    @Override
    // EFFECTS: returns this as a JSON object
    // Source: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    //         this method was based on the ones present in the Thingy and WorkRoom classes
    public JSONObject makeJsonObject() {
        JSONObject json = new JSONObject();
        json.put("input", input);
        return json;
    }

    //getters and setters

    public String getLetter() {
        return this.input;
    }

    public void setLetterInput(String newInput) {
        this.input = newInput;
    }

    // EFFECTS: set the input to capital and returns the letterinput object
    public LetterInput setCapital() {
        this.input = this.input.toUpperCase();

        return this;
    }

    // EFFECTS: set the input to lower case and returns the letterinput object
    public LetterInput setLowerCase() {
        this.input = this.input.toLowerCase();

        return this;
    }
}
