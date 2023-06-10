package ui;

import javax.swing.*;
import java.awt.*;

// represents all the hangman stages as ImageIcons in JLabels
public class HangManStageIcons {
    private Icon stage0;
    private Icon stage1;
    private Icon stage2;
    private Icon stage3;
    private Icon stage4;
    private Icon stage5;
    private Icon stage6;

    // EFFECTS: instantiates all required hangman images
    public HangManStageIcons() {
        stage0 = scaledImage("hangman-stage0.png");
        stage1 = scaledImage("hangman-stage1.png");
        stage2 = scaledImage("hangman-stage2.png");
        stage3 = scaledImage("hangman-stage3.png");
        stage4 = scaledImage("hangman-stage4.png");
        stage5 = scaledImage("hangman-stage5.png");
        stage6 = scaledImage("hangman-stage6.png");
    }

    // REQUIRES: existing file path
    // EFFECTS: returns a scaled image from file path
    private ImageIcon scaledImage(String file) {
        ImageIcon temp = new ImageIcon(file);
        Image getTempImage = temp.getImage();
        Image newScaledImage = getTempImage.getScaledInstance(HangManAppGUI.WIDTH / 3,
                HangManAppGUI.HEIGHT / 2, Image.SCALE_DEFAULT);
        return new ImageIcon(newScaledImage);
    }

    // getters

    public Icon getStage0() {
        return stage0;
    }

    public Icon getStage1() {
        return stage1;
    }

    public Icon getStage2() {
        return stage2;
    }

    public Icon getStage3() {
        return stage3;
    }

    public Icon getStage4() {
        return stage4;
    }

    public Icon getStage5() {
        return stage5;
    }

    public Icon getStage6() {
        return stage6;
    }
}