package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        new HangManAppGUI();
        try {
            new HangManApp();
        } catch (FileNotFoundException e) {
            System.err.println("Failed to run game: file was not found!");
        }
    }
}
