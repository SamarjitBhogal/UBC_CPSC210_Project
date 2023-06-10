package ui;

import javax.swing.*;
import java.awt.*;

// represents all the components required for the hang-man main menu, including title, and buttons
// The code in this class was influenced/learnt by this thread:
// Source: https://stackoverflow.com/questions/23034675/how-to-build-a-java-main-menu
public class MainMenu extends JPanel {
    private final JLabel title;
    private final JLabel author;
    private final JButton play;
    private final JButton save;
    private final JButton load;
    private final JButton quit;
    private final GridBagConstraints constraints;

    // EFFECTS: creates a main menu panel for the hangman game
    public MainMenu() {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();
        title = new JLabel("Hang-Man Game");
        author = new JLabel(" By: Samarjit Bhogal");
        play = new JButton("Play");
        save = new JButton("Save");
        load = new JButton("Load");
        quit = new JButton("Quit");
        setMenuComponentsSizes();
        addButtonsAndTitle();
    }

    // MODIFIES: this
    // EFFECTS: set the required sizes for all buttons and labels in this class
    private void setMenuComponentsSizes() {
        play.setPreferredSize(new Dimension(200, 50));
        save.setPreferredSize(new Dimension(200, 50));
        load.setPreferredSize(new Dimension(200, 50));
        quit.setPreferredSize(new Dimension(200, 50));
        setMenuComponentsFonts();
    }

    // MODIFIES: this
    // EFFECTS: set the proper fronts for all buttons and labels in this class
    private void setMenuComponentsFonts() {
        title.setFont(new Font("Bold", Font.BOLD, 100));
        author.setFont(new Font("Bold", Font.BOLD, 35));
        play.setFont(new Font("Bold", Font.BOLD, 15));
        save.setFont(new Font("Bold", Font.BOLD, 15));
        load.setFont(new Font("Bold", Font.BOLD, 15));
        quit.setFont(new Font("Bold", Font.BOLD, 15));
    }

    // MODIFIES: this
    // EFFECTS: adds all buttons and labels to the super panel
    private void addButtonsAndTitle() {
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(title, constraints);

        constraints.gridy = 1;
        add(play, constraints);

        constraints.gridy = 2;
        add(save, constraints);

        constraints.gridy = 3;
        add(load, constraints);

        constraints.gridy = 4;
        add(quit, constraints);

        constraints.gridy = 5;
        add(author, constraints);
    }

    // getters

    public JButton getPlay() {
        return this.play;
    }

    public JButton getSave() {
        return this.save;
    }

    public JButton getLoad() {
        return this.load;
    }

    public JButton getQuit() {
        return this.quit;
    }
}
