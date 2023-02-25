package tictactoe;

import javax.swing.*;
import java.awt.*;

public class CustomJButton extends JButton {
    public CustomJButton(String name) {
        setBackground(Color.GRAY);
        setName(name);
        setText(" ");
        setFont(getFont().deriveFont(Font.BOLD,44));
        setFocusPainted(false);
    }
}
