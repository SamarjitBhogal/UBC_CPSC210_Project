package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// Graphical Hangman game/application
public class HangManAppGUI extends JFrame implements ActionListener {
    public static final int HEIGHT = 900;
    public static final int WIDTH = 1500;

    private static final String HANG_MAN_SAVE = "./data/HangMan.json";
    private static final String GUESS_WORD_SAVE = "./data/GuessWord.json";
    private static final String SCORE_SAVE = "./data/Score.json";

    private GuessWord guessWord;
    private HangMan hangMan;
    private Score score;
    private LetterInput letterInput;
    private JsonWriter writer;
    private JsonReader reader;

    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel rightSidePanel;
    private JPanel leftSidePanel;
    private JPanel gameButtons;
    private JLabel hangManStageLabelIcon;
    private JLabel blankWordLabel;
    private JLabel scoreLabel;
    private JLabel prevInputsLabel;
    private JLabel guessesRemainingLabel;
    private JButton returnToMenus;
    private JButton skipWord;
    private JTextArea prevInputsArea;
    private MainMenu mainMenu;
    private InputBar inputBar;
    private HangManStageIcons hangManStageIcons;

    // EFFECTS: instantiates all fields and graphics required for the game,
    //          and add this frame to key listener
    public HangManAppGUI() {
        super("Hang-Man Game");
        initializeFields();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes HangMan, Score, json writer, json reader, empty lift prev inputs
    private void initializeFields() {
        this.hangMan = new HangMan();
        this.score = new Score();
        this.guessWord = new GuessWord("Dog");
        this.writer = new JsonWriter(HANG_MAN_SAVE);
        this.reader = new JsonReader(HANG_MAN_SAVE);
    }

    // MODIFIES: this
    // EFFECTS: set the JFrame window in which this hangman game will operate,
    //          and adds all necessary game components visually.
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initializeMenu();
        setVisible(true);
        validate();
    }

    // MODIFIES: this
    // EFFECTS: instantiates the main menu and adds it to the frame
    private void initializeMenu() {
        mainMenu = new MainMenu();
        add(mainMenu);
        addMenuButtonsToListener();
    }

    // MODIFIES: this
    // EFFECTS: helper method to add main menu buttons to action listener
    private void addMenuButtonsToListener() {
        mainMenu.getPlay().addActionListener(this);
        mainMenu.getSave().addActionListener(this);
        mainMenu.getLoad().addActionListener(this);
        mainMenu.getQuit().addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: executes all the user's actions to pressing one of the available buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainMenu.getPlay()) {
            setupGame();
        } else if (e.getSource() == mainMenu.getSave()) {
            saveGame();
        } else if (e.getSource() == mainMenu.getLoad()) {
            loadGame();
        } else if (e.getSource() == mainMenu.getQuit()) {
            quitGame();
        } else if (e.getSource() == returnToMenus) {
            returnToMenuProcess();
        } else if (e.getSource() == skipWord) {
            skipWord();
        } else {
            runGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all necessary game components from the main panel to the frame
    private void setupGame() {
        initializePanels();
        initializeScene();
    }

    // MODIFIES: this
    // EFFECTS: instantiates all required panels
    private void initializePanels() {
        gamePanel = new JPanel(new GridBagLayout());
        mainPanel = new JPanel(new BorderLayout());
        rightSidePanel = new JPanel(new BorderLayout());
        leftSidePanel = new JPanel(new BorderLayout());
        gameButtons = new JPanel(new GridBagLayout());
        inputBar = new InputBar();
    }

    // MODIFIES: this
    // EFFECTS: sets up the game scene
    private void initializeScene() {
        remove(mainMenu);
        initializeGame();
        initializeInputBar();
        repaint();
        add(mainPanel, BorderLayout.CENTER);
        validate();
    }

    // MODIFIES: this
    // EFFECTS: instantiates and sets up all necessary components for the game
    private void initializeGame() {
        GridBagConstraints constraints = new GridBagConstraints();
        initializeAndSetGameComponents();

        gamePanel.setSize(WIDTH, HEIGHT);

        gameButtons.add(returnToMenus);
        gameButtons.add(skipWord);

        constraints.gridx = 0;
        constraints.gridy = 1;
        gamePanel.add(gameButtons, constraints);

        constraints.gridy = 2;
        gamePanel.add(hangManStageLabelIcon, constraints);

        constraints.gridy = 3;
        gamePanel.add(blankWordLabel, constraints);

        rightSidePanel.add(scoreLabel, BorderLayout.CENTER);
        rightSidePanel.add(guessesRemainingLabel, BorderLayout.NORTH);
        leftSidePanel.add(prevInputsArea, BorderLayout.CENTER);
        leftSidePanel.add(prevInputsLabel, BorderLayout.NORTH);

        mainPanel.add(gamePanel, BorderLayout.NORTH);
        mainPanel.add(rightSidePanel, BorderLayout.EAST);
        mainPanel.add(leftSidePanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: instantiates and sets up all required game components
    private void initializeAndSetGameComponents() {
        gamePanel.setSize(WIDTH, HEIGHT);
        setUpMenuReturnButton();
        setUpSkipButton();
        setUpHangManIcons();
        setUpBlankWordLabel();
        setUpScoreLabel();
        setUpPrevInputsLabelAndArea();
        setUpGuessesRemainingLabel();
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up a return to menu button
    private void setUpMenuReturnButton() {
        returnToMenus = new JButton("Main Menu");
        returnToMenus.setFont(new Font("Bold", Font.BOLD, 15));
        returnToMenus.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up a skip word button
    private void setUpSkipButton() {
        skipWord = new JButton("Skip Word");
        skipWord.setFont(new Font("Bold", Font.BOLD, 15));
        skipWord.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up hang man icons
    private void setUpHangManIcons() {
        hangManStageIcons = new HangManStageIcons();
        hangManStageLabelIcon = new JLabel(getHangManStageIcon(hangMan.getCurrentStage()));
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up a blank word label
    private void setUpBlankWordLabel() {
        blankWordLabel = new JLabel(makeSpacedOutBlankWord(guessWord.getBlankWord()));
        blankWordLabel.setFont(new Font("Bold", Font.BOLD, 80));
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up a score label
    private void setUpScoreLabel() {
        scoreLabel = new JLabel("Score: " + score.getScore());
        scoreLabel.setFont(new Font("Bold", Font.BOLD, 35));
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up a label describing the text area below it
    //          as well as the text area itself
    private void setUpPrevInputsLabelAndArea() {
        prevInputsLabel = new JLabel("Letters You Have Guessed:");
        prevInputsLabel.setFont(new Font("Bold", Font.BOLD, 20));
        prevInputsArea = new JTextArea(letterInputHistoryAsString(guessWord.getLetterInputHistory()));
        prevInputsArea.setFont(new Font("Bold", Font.BOLD, 15));
        prevInputsArea.setPreferredSize(new Dimension(200, 20));
        prevInputsArea.setEditable(false);
    }

    // MODIFIES: this
    // EFFECTS: helper method to instantiate and set up a guesses remaining label
    private void setUpGuessesRemainingLabel() {
        guessesRemainingLabel = new JLabel("Guesses Remaining: " + hangMan.getGuessesRemaining());
        guessesRemainingLabel.setFont(new Font("Bold", Font.BOLD, 35));
    }

    // EFFECTS: returns a spaced out version of the current blankWord
    private String makeSpacedOutBlankWord(List<LetterInput> blankWord) {
        int lastLetterIndex = blankWord.size() - 1;
        StringBuilder spacedOutBlankWord = new StringBuilder();

        for (LetterInput li: blankWord) {
            if (blankWord.get(lastLetterIndex) == li) {
                spacedOutBlankWord.append(li.getLetter());
            } else {
                spacedOutBlankWord.append(li.getLetter()).append(" ");
            }
        }
        return spacedOutBlankWord.toString();
    }

    // MODIFIES: this
    // EFFECTS: adds text panel for user letter input
    private void initializeInputBar() {
        inputBar = new InputBar();
        inputBar.getEnter().addActionListener(this);
        mainPanel.add(inputBar, BorderLayout.SOUTH);
    }

    // EFFECTS: exits the system processes and closes the application
    private void quitGame() {
        printLog(EventLog.getInstance());
        System.exit(0);
    }

    // EFFECTS: prints all the events that have been logged since the
    //          application was run to the console
    private void printLog(EventLog el) {
        for (Event e: el) {
            System.out.println(e.toString());
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: runs and manages all the process for a hangman game for the user
    private void runGame() {
        makeInputFromInputBar();
        List<Integer> indexes = guessWord.findIndexes(letterInput);

        if (checkIfAlreadyGuessed(letterInput)) {
            JOptionPane.showMessageDialog(this,
                    "You Have Already Guessed This Letter! Try Another One!");
        } else if (letterInput.isValid()) {
            if ((indexes.size() > 0)) {
                doRightLetterProcesses();
                if (guessWord.isGuessedCompletely()) {
                    doFinishedWordProcesses();
                }
            } else {
                if (hangMan.getCurrentStage() == 5) {
                    doWrongLetterProcesses();
                    doFailedWordProcesses();
                } else {
                    doWrongLetterProcesses();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
        inputBar.getTextField().setText("");
    }

    // MODIFIES: this
    // EFFECTS: skips the current word and updates the hangman icon, score board, prev letters guessed
    //          and shows the skipped word as a message dialog
    private void skipWord() {
        String wordToSkip = guessWord.getWord();

        hangMan.resetHangMan();
        guessWord.clearInputHistory();
        guessWord.skipWordToGuess(guessWord.getNextWord());
        score.decreaseScoreOnSkip();
        hangManStageLabelIcon.setIcon(getHangManStageIcon(hangMan.getCurrentStage()));
        prevInputsArea.setText("");
        guessesRemainingLabel.setText("Guesses Remaining: " + hangMan.getGuessesRemaining());
        scoreLabel.setText("Score: " + score.getScore());
        blankWordLabel.setText(makeSpacedOutBlankWord(guessWord.getBlankWord()));
        JOptionPane.showMessageDialog(this,
                "The word was: " + wordToSkip,
                "Skip",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: makes a letterinput from the letter typed in the inputBar
    private void makeInputFromInputBar() {
        String letter = inputBar.getTextField().getText();
        letterInput = new LetterInput(letter);
    }

    // EFFECTS: returns true if this letter input has already been guessed
    private Boolean checkIfAlreadyGuessed(LetterInput input) {
        boolean isGuessed = false;
        String inputLetter = input.getLetter();
        for (LetterInput i: guessWord.getLetterInputHistory()) {
            if (i.getLetter().equalsIgnoreCase(inputLetter)) {
                isGuessed = true;
                break;
            }
        }
        return isGuessed;
    }

    // MODIFIES: this
    // EFFECTS: adds the letter the blank word and updates the label
    private void doRightLetterProcesses() {
        guessWord.addLetter(letterInput);
        prevInputsArea.setText(letterInputHistoryAsString(guessWord.getLetterInputHistory()));
        blankWordLabel.setText(makeSpacedOutBlankWord(guessWord.getBlankWord()));
    }

    // MODIFIES: this
    // EFFECTS: updates hangman, prevInputs label, guessesRemainingLabel
    //          and changes the icon to match the current stage
    private void doWrongLetterProcesses() {
        guessWord.addInputToHistory(letterInput);
        hangMan.updateStageAndGuesses();
        prevInputsArea.setText(letterInputHistoryAsString(guessWord.getLetterInputHistory()));
        guessesRemainingLabel.setText("Guesses Remaining: " + hangMan.getGuessesRemaining());
        hangManStageLabelIcon.setIcon(getHangManStageIcon(hangMan.getCurrentStage()));
    }

    // EFFECTS: returns the current list of prevInputs as a string
    private String letterInputHistoryAsString(List<LetterInput> prevInputs) {
        int lastInputIndex = prevInputs.size() - 1;
        StringBuilder spacedOutBlankWord = new StringBuilder();

        for (LetterInput li: prevInputs) {
            if (prevInputs.get(lastInputIndex) == li) {
                spacedOutBlankWord.append(li.getLetter());
            } else {
                spacedOutBlankWord.append(li.getLetter()).append(", ");
            }
        }
        return spacedOutBlankWord.toString();
    }

    // MODIFIES: this
    // EFFECTS: runs all the necessary processes when the user guesses the word correctly:
    //          calls prepareNewRound and Presents a window to the user asking if they want another round.
    // Source: JOptionPane code in this method was learnt and based on the code presented in this video:
    //         https://www.youtube.com/watch?v=4edL_cwmiZ4 at 3:40
    private void doFinishedWordProcesses() {
        int response = JOptionPane.showConfirmDialog(this,
                "Well Done! You Guessed The Word Correctly! Play Another Round?",
                "",
                JOptionPane.YES_NO_OPTION);

        prepareNewRound();
        score.increaseScore();
        scoreLabel.setText("Score: " + score.getScore());
        prevInputsArea.setText("");

        if (response == JOptionPane.NO_OPTION) {
            returnToMenuProcess();
        }
    }

    // MODIFIES: this
    // EFFECTS: runs all the necessary processes when the user fails to guess the word correctly:
    //          calls prepareNewRound and Presents a window to the user asking if they want another round.
    // Source: JOptionPane code in this method was learnt and based on the code presented in this video:
    //         https://www.youtube.com/watch?v=4edL_cwmiZ4 at 3:40
    private void doFailedWordProcesses() {
        int response = JOptionPane.showConfirmDialog(this,
                "Game Over! Play Another Round?",
                "",
                JOptionPane.YES_NO_OPTION);

        prepareNewRound();
        score.decreaseScore();
        scoreLabel.setText("Score: " + score.getScore());
        prevInputsArea.setText(letterInputHistoryAsString(guessWord.getLetterInputHistory()));

        if (response == JOptionPane.NO_OPTION) {
            returnToMenuProcess();
        }
    }

    // MODIFIES: this
    // EFFECTS: a helper method to reset the hangman, reset the hangman icon, clears
    //          the prevInputs list changes guessword to another word, updates the blank word label.
    private void prepareNewRound() {
        hangMan.resetHangMan();
        guessWord.clearInputHistory();
        hangManStageLabelIcon.setIcon(getHangManStageIcon(hangMan.getCurrentStage()));
        guessWord.changeWordToGuess(guessWord.getNextWord());
        blankWordLabel.setText(makeSpacedOutBlankWord(guessWord.getBlankWord()));
    }

    // MODIFIES: this
    // EFFECTS: removes the main panel, repaints the frame, and adds the main menu panel
    private void returnToMenuProcess() {
        mainPanel.removeAll();
        remove(mainPanel);
        repaint();
        add(mainMenu);
        validate();
    }

    // EFFECTS: returns the proper hangman icon based on the current stage
    private Icon getHangManStageIcon(int stage) {
        if (stage == 0) {
            return hangManStageIcons.getStage0();
        } else if (stage == 1) {
            return hangManStageIcons.getStage1();
        } else if (stage == 2) {
            return hangManStageIcons.getStage2();
        } else if (stage == 3) {
            return hangManStageIcons.getStage3();
        } else if (stage == 4) {
            return hangManStageIcons.getStage4();
        } else if (stage == 5) {
            return hangManStageIcons.getStage5();
        } else  {
            return hangManStageIcons.getStage6();
        }
    }

    // EFFECTS: saves all game content to their corresponding save destinations
    private void saveGame() {
        saveGuessWord();
        saveHangMan();
        saveScore();
        JOptionPane.showMessageDialog(this, "Game Saved!");
    }

    // EFFECTS: saves guessword to file
    private void saveGuessWord() {
        writer.setDestination(GUESS_WORD_SAVE);
        try {
            writer.open();
            writer.writeGW(guessWord);
            writer.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to write to file: " + GUESS_WORD_SAVE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this,
                    "Failed to write to file: " + HANG_MAN_SAVE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this,
                    "Failed to write to file: " + SCORE_SAVE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads all game content from save destinations
    private void loadGame() {
        loadGuessWord();
        loadHangMan();
        loadScore();
        JOptionPane.showMessageDialog(this, "Loaded game! Press Play On Title!");
    }

    // MODIFIES: this
    // EFFECTS: loads guessword from save destination
    private void loadGuessWord() {
        reader.setSource(GUESS_WORD_SAVE);
        try {
            guessWord = reader.readGuessWord();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to read from file: " + GUESS_WORD_SAVE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads hangman from save destination
    private void loadHangMan() {
        reader.setSource(HANG_MAN_SAVE);
        try {
            hangMan = reader.readHangMan();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to read from file: " + HANG_MAN_SAVE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads score from save destination
    private void loadScore() {
        reader.setSource(SCORE_SAVE);
        try {
            score = reader.readScore();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to read from file: " + SCORE_SAVE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
