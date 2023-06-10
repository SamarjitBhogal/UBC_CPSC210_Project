package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a hangman at its current stage, and the number of guesses remaining:
// 0 -> no hangman
// 1 -> hangman's head forms
// 2 -> hangman's torso forms
// 3 -> hangman's left arm forms
// 4 -> hangman's right arm forms
// 5 -> hangman's left leg forms
// 6 -> hangman's right leg forms. Oh no! The hangman is here! Game over!
public class HangMan implements Writable {
    private int stage;
    private int guesses;

    // EFFECTS: creates a Hangman with the current stage set initially at 0,
    //          and the number of guesses remaining to be 6
    public HangMan() {
        this.stage = 0;
        this.guesses = 6;
    }

    // MODIFIES: this
    // EFFECTS: increases/updates the current stage of the hangman by 1,
    //          with each increase, the number of guesses remaining
    //          decreases by 1,
    public void updateStageAndGuesses() {
        this.stage++;
        updateGuesses();
    }

    // MODIFIES: this
    // EFFECTS: updates the number of guesses remaining by decreasing it by 1
    public void updateGuesses() {
        this.guesses--;
    }

    // MODIFIES: this
    // EFFECTS: resets the current stage to 0 and guesses remaining to 6
    public void resetHangMan() {
        this.stage = 0;
        this.guesses = 6;
    }

    @Override
    // EFFECTS: returns this as a JSON object
    // Source: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    //         this method was based on the ones present in the Thingy and WorkRoom classes
    public JSONObject makeJsonObject() {
        JSONObject json = new JSONObject();
        json.put("stage", stage);
        json.put("guesses", guesses);

        return json;
    }

    // getters and setters

    public int getCurrentStage() {
        return this.stage;
    }

    public int getGuessesRemaining() {
        return this.guesses;
    }

    // REQUIRES: stage to be [0, 6]
    public void setStage(int stage) {
        this.stage = stage;
    }

    // REQUIRES: stage to be [0, 6]
    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }
}
