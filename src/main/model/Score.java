package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a game score having a score value
// the score will count every word guessed correctly (100 points) before hangman forms
public class Score implements Writable {
    private int score;

    // EFFECTS: creates a score with initial score as zero
    public Score() {
        this.score = 0;
    }

    // MODIFIES: this
    // EFFECTS: increases the score by 100 points
    public void increaseScore() {
        this.score = this.score + 100;
    }

    // REQUIRES: this to not be < 0
    // MODIFIES: this
    // EFFECTS: decreases the score by 100 points
    public void decreaseScore() {
        int temp = this.score;

        if (!(temp - 100 < 0)) {
            this.score = this.score - 100;
        } else {
            this.score = 0;
        }
    }

    // REQUIRES: this to not be < 0
    // MODIFIES: this
    // EFFECTS: decreases the score by 50 points
    public void decreaseScoreOnSkip() {
        int temp = this.score;

        if (!(temp - 50 < 0)) {
            this.score = this.score - 50;
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the score
    public void resetScore() {
        this.score = 0;
    }

    @Override
    // EFFECTS: returns this as a JSON object
    // Source: JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    //         this method was based on the ones present in the Thingy and WorkRoom classes
    public JSONObject makeJsonObject() {
        JSONObject json = new JSONObject();
        json.put("score", score);
        return json;
    }

    // getters and setters

    public int getScore() {
        return this.score;
    }

    public void setScore(int value) {
        this.score = value;
    }
}
