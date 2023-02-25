package tictactoe;

import javax.swing.*;
import java.awt.*;

public class ControlJButton extends JButton {

    public ControlJButton(String text) {
        setBackground(Color.GRAY);
        setText(text);
        setFont(getFont().deriveFont(Font.BOLD));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setFocusPainted(false);
    }
}
