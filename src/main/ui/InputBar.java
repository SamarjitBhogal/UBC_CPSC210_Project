package ui;

import javax.swing.*;
import java.awt.*;

// represents the games input bar for letters, including a text field, enter button, and a label
public class InputBar extends JPanel {
    private JTextField textField;
    private JButton enter;
    private JLabel label;
    private GridBagConstraints constraints;

    // EFFECTS: constructs the input bar with textfield, label, enter button
    public InputBar() {
        super(new GridBagLayout());
        constraints = new GridBagConstraints();
        label = new JLabel("Make a guess:");
        textField = new JTextField();
        enter = new JButton("Enter");
        setLabel();
        setTextField();
        setButton();
    }

    // MODIFIES: this
    // EFFECTS: sets the font and dimensions of the label and adds it to super
    private void setLabel() {
        label.setFont(new Font("Bold", Font.BOLD, 20));
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(label, constraints);
    }

    // MODIFIES: this
    // EFFECTS: sets the font and dimensions of the text field adds it to super
    private void setTextField() {
        textField.setFont(new Font("Bold", Font.BOLD, 40));
        textField.setPreferredSize(new Dimension(250, 50));
        constraints.gridy = 2;
        add(textField);
    }

    // MODIFIES: this
    // EFFECTS: adds the button to super
    private void setButton() {
        add(enter);
    }

    // getters

    public JButton getEnter() {
        return enter;
    }

    public JTextField getTextField() {
        return textField;
    }
}
