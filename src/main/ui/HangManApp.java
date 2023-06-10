package ui;

import model.GuessWord;
import model.HangMan;
import model.LetterInput;
import model.Score;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Hangman game/application
public class HangManApp {
    private static final String HANG_MAN_SAVE = "./data/HangMan.json";
    private static final String GUESS_WORD_SAVE = "./data/GuessWord.json";
    private static final String SCORE_SAVE = "./data/Score.json";

    private GuessWord guessWord;
    private HangMan hangMan;
    private Score score;
    private LetterInput letterInput;
    private Scanner input;
    private JsonWriter writer;
    private JsonReader reader;

    // EFFECTS: run the hangman game
    public HangManApp() throws FileNotFoundException {
        initializeFields();
        runHangManGame();
    }

    // MODIFIES: this
    // EFFECTS: initializes HangMan, Score, json writer, json reader, and Scanner
    private void initializeFields() {
        this.hangMan = new HangMan();
        this.score = new Score();
        this.guessWord = new GuessWord("Dog");
        this.input = new Scanner(System.in);
        this.writer = new JsonWriter(HANG_MAN_SAVE);
        this.reader = new JsonReader(HANG_MAN_SAVE);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // Source: TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    //         gave me an idea of how to write a run method for my app
    private void runHangManGame() {
        boolean on = true;
        String input;

        while (on) {
            gameMenu();
            input = this.input.next().toLowerCase();

            if (input.equals("q")) {
                on = false;
                System.out.println("See you later!");
            } else {
                processMenuInput(input);
            }
        }
    }

    // EFFECTS: displays menu options to the user
    private void gameMenu() {
        System.out.println("Hang-man Game");
        System.out.println("\tMenu:");
        System.out.println("\tType: \"p\" to Play");
        System.out.println("\tType: \"s\" to Save Game");
        System.out.println("\tType: \"l\" to Load a Save");
        System.out.println("\tType: \"q\" to Quit");
    }

    // EFFECTS: processes user menu input
    private void processMenuInput(String input) {
        if (input.equals("p")) {
            startGame();
        } else if (input.equals("s")) {
            saveGame();
        } else if (input.equals("l")) {
            loadGame();
        } else {
            System.out.println("Not a valid key...");
        }
    }

    // MODIFIES: this
    // EFFECTS: starts the game by displaying scores,
    //          hangman status and current blank word
    // Source: TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    //         gave me an idea of how to write my startGame method
    private void startGame() {
        boolean on = true;
        setupGame();

        while (on) {
            if (guessWord.isGuessedCompletely()) {
                doDoneProcesses();
                on = false;
            } else {
                if (!(on = manageInput())) {
                    break;
                }
                if (this.letterInput.isValid()) {
                    if ((this.guessWord.findIndexes(this.letterInput).size() > 0)) {
                        doCorrectLetterProcesses();
                    } else if (this.hangMan.getGuessesRemaining() == 1) {
                        on = doGameOverProcesses();
                    } else {
                        doWrongLetterProcesses();
                    }
                } else {
                    System.out.println("Input not valid...");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets the previous inputs in the list and prints essential game info
    private void setupGame() {
        guessWord.clearInputHistory();
        printEssentials();
    }

    // EFFECTS: prints the current blank word, hangman status, scores, and guesses remaining in one go
    private void printEssentials() {
        System.out.println("Type: \"/\" anytime to return to menu!");
        printScore();
        printHangManStatus(this.hangMan.getCurrentStage());
        printGuessesRemains(this.hangMan.getGuessesRemaining());
        printBlankWord();
    }

    // MODIFIES: this
    // EFFECTS: initializes the input needed to run user inputs for startGame method,
    //          if input equals "/" return false, otherwise true
    private Boolean manageInput() {
        String input = this.input.next();
        if (checkIfAlreadyGuessed(input)) {
            System.out.println("You have already guessed this letter! Try Another!");

            return manageInput();
        } else if (!input.equals("/")) {
            this.letterInput = new LetterInput(input);

            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns true if this letter has already been guessed, false otherwise
    private Boolean checkIfAlreadyGuessed(String input) {
        boolean isGuessed = false;
        for (LetterInput i: guessWord.getLetterInputHistory()) {
            if (input.equalsIgnoreCase(i.getLetter())) {
                isGuessed = true;
                break;
            }
        }
        return isGuessed;
    }

    // MODIFIES: this
    // EFFECTS: runs the processes needed when the word is fully guessed
    private void doDoneProcesses() {
        score.increaseScore();
        this.guessWord.changeWordToGuess(this.guessWord.getNextWord());
        this.hangMan.resetHangMan();
        printDoneMessage();
    }

    // EFFECTS: prints the appropriate messages when a word is fully guessed
    private void printDoneMessage() {
        System.out.println("You did it! You got 100 more points! Play another round?");
    }

    // EFFECTS: runs the processes needed when a letter is guessed correctly
    private void doCorrectLetterProcesses() {
        this.guessWord.addLetter(this.letterInput);
        printCorrectLetterMessage();
    }

    // EFFECTS: prints the appropriate messages when a letter is guessed correctly
    private void printCorrectLetterMessage() {
        System.out.println("Well done!");
        System.out.println(this.guessWord.getBlankStrWord());
    }

    // EFFECTS: prints the current score
    private void printScore() {
        System.out.println("Current score: " + this.score.getScore());
    }

    // EFFECTS: prints the current blank word to guess
    private void printBlankWord() {
        System.out.println("What is this word?: " + this.guessWord.constructBlankWordStr());
    }

    // EFFECTS: runs the processes needed when the game is over and returns false always
    //          to stop the game
    private Boolean doGameOverProcesses() {
        this.score.decreaseScore();
        this.hangMan.updateStageAndGuesses();
        this.guessWord.changeWordToGuess(this.guessWord.getNextWord());
        printFailMessage();
        return false;
    }

    // EFFECTS: prints the appropriate game over messages when hangman is fully formed
    private void printFailMessage() {
        printHangManStatus(this.hangMan.getCurrentStage());
        System.out.println("Game over! Hang-man is here!");
        System.out.println("You lost 100 points!");
    }

    // EFFECTS: runs the processes needed when a wrong letter is guessed
    private void doWrongLetterProcesses() {
        guessWord.addInputToHistory(this.letterInput);
        this.hangMan.updateStageAndGuesses();
        printWrongLetterMessage();
    }

    // EFFECTS: prints the appropriate messages when a letter is guessed incorrectly
    private void printWrongLetterMessage() {
        printHangManStatus(this.hangMan.getCurrentStage());
        printGuessesRemains(this.hangMan.getGuessesRemaining());
        printBlankWord();
    }

    // EFFECTS: prints current status of hangman based on the state number
    private void printHangManStatus(int stage) {
        if (stage == 0) {
            System.out.println("No sign of Hangman yet!");
        } else if (stage == 1) {
            System.out.println("Oh no! There is now a head!");
        } else if (stage == 2) {
            System.out.println("Oh no! There is now a torso!");
        } else if (stage == 3) {
            System.out.println("Oh no! There is now a left arm!");
        } else if (stage == 4) {
            System.out.println("Oh no! There is now a right arm!");
        } else if (stage == 5) {
            System.out.println("Oh no! There is now a left leg!");
        } else if (stage == 6) {
            System.out.println("Oh no! There is now a right leg!");
        }
    }

    // EFFECTS: prints current guesses remaining
    private void printGuessesRemains(int num) {
        if (num == 6) {
            System.out.println("You have " + num + " guesses remaining!");
        } else if (num == 5) {
            System.out.println("You have " + num + " guesses remaining!");
        } else if (num == 4) {
            System.out.println("You have " + num + " guesses remaining!");
        } else if (num == 3) {
            System.out.println("You have " + num + " guesses remaining!");
        } else if (num == 2) {
            System.out.println("You have " + num + " guesses remaining!");
        } else if (num == 1) {
            System.out.println("You have one last guess!");
        }
    }

    // EFFECTS: saves all game content to their corresponding save destinations
    private void saveGame() {
        saveGuessWord();
        saveHangMan();
        saveScore();
        System.out.println("Game saved!");
    }

    // EFFECTS: saves guessword to file
    private void saveGuessWord() {
        writer.setDestination(GUESS_WORD_SAVE);
        try {
            writer.open();
            writer.writeGW(guessWord);
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Failed to write to file: " + GUESS_WORD_SAVE);
        }
    }

    // EFFECTS: saves hangman to file
    private void saveHangMan() {
        writer.setDestination(HANG_MAN_SAVE);
        try {
            writer.open();
            writer.writeHM(hangMan);
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Failed to write to file: " + HANG_MAN_SAVE);
        }
    }

    // EFFECTS: saves score to file
    private void saveScore() {
        writer.setDestination(SCORE_SAVE);
        try {
            writer.open();
            writer.writeS(score);
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("Failed to write to file: " + SCORE_SAVE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all game content from save destinations
    private void loadGame() {
        loadGuessWord();
        loadHangMan();
        loadScore();
        System.out.println("Loaded game!");
    }

    // MODIFIES: this
    // EFFECTS: loads guessword from save destination
    private void loadGuessWord() {
        reader.setSource(GUESS_WORD_SAVE);
        try {
            guessWord = reader.readGuessWord();
        } catch (IOException e) {
            System.err.println("Failed to read from file: " + GUESS_WORD_SAVE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads hangman from save destination
    private void loadHangMan() {
        reader.setSource(HANG_MAN_SAVE);
        try {
            hangMan = reader.readHangMan();
        } catch (IOException e) {
            System.err.println("Failed to read from file: " + HANG_MAN_SAVE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads score from save destination
    private void loadScore() {
        reader.setSource(SCORE_SAVE);
        try {
            score = reader.readScore();
        } catch (IOException e) {
            System.err.println("Failed to read from file: " + SCORE_SAVE);
        }
    }

//    // EFFECTS: saves all game content to current save field
//    private void saveGame() {
//        writer.setDestination(HANG_MAN_SAVE);
//
//        try {
//            writer.open();
//            writer.writeGW(guessWord);
//            writer.writeHM(hangMan);
//            writer.writeLI(letterInput);
//            writer.writeS(score);
//            writer.close();
//        } catch (FileNotFoundException e) {
//            System.err.println("Failed to save to file: " + HANG_MAN_SAVE);
//        }
//        System.out.println("Game saved!");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads all game content current save field
//    private void loadGame() {
//        reader.setSource(HANG_MAN_SAVE);
//
//        try {
//            this.guessWord = reader.readGuessWord();
//            this.hangMan = reader.readHangMan();
//            this.letterInput = reader.readLetterInput();
//            this.score = reader.readScore();
//        } catch (IOException e) {
//            System.err.println("Failed to read from file: " + HANG_MAN_SAVE);
//        }
//        System.out.println("Loaded game!");
//    }
}
