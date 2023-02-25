package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TicTacToe extends JFrame {
    static final int ROWS = 3;
    static final int COLUMNS = ROWS;

    static String turnPlayer = "X";

    static boolean gameOver = false;

    static Status status = Status.NOT_STARTED;

    static final JButton[][] buttons = new JButton[ROWS][COLUMNS];

    static String player1 = "Human";
    static String player2 = "Human";

    static final JButton[] flattenedButtons = new JButton[ROWS * COLUMNS];
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setSize(450, 554);
        setResizable(false);
        setVisible(true);
        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 25));

        JPanel board = new JPanel(new GridLayout(3, 3, 5, 10));
        board.setSize(getWidth() - 30, getWidth() - 30);
        board.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        // add methods to add these two elements?
        JLabel labelStatus = new JLabel(String.valueOf(Status.NOT_STARTED));
        labelStatus.setName("LabelStatus");
        labelStatus.setSize(200, 50);
        labelStatus.setFont(labelStatus.getFont().deriveFont(Font.BOLD));

        addControlButtons(buttonsPanel, labelStatus);
        add(buttonsPanel, BorderLayout.PAGE_START);

        addButtons(board, labelStatus);
        add(board, BorderLayout.CENTER);

        progressPanel.add(labelStatus, BorderLayout.WEST);
        add(progressPanel, BorderLayout.PAGE_END);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        addMenusToBar(menuBar, buttonsPanel);

        setVisible(true);
    }

    private void addMenusToBar(JMenuBar menuBar, JPanel buttonsPanel) {
        JMenu menuGame = new JMenu("Game");
        menuGame.setName("MenuGame");
        menuBar.add(menuGame);

        JMenuItem menuHumanHuman = new JMenuItem("Human vs Human");
        menuHumanHuman.setName("MenuHumanHuman");
        menuGame.add(menuHumanHuman);

        JMenuItem menuHumanRobot = new JMenuItem("Human vs Robot");
        menuHumanRobot.setName("MenuHumanRobot");
        menuGame.add(menuHumanRobot);

        JMenuItem menuRobotHuman = new JMenuItem("Robot vs Human");
        menuRobotHuman.setName("MenuRobotHuman");
        menuGame.add(menuRobotHuman);

        JMenuItem menuRobotRobot = new JMenuItem("Robot vs Robot");
        menuRobotRobot.setName("MenuRobotRobot");
        menuGame.add(menuRobotRobot);

        menuGame.addSeparator();

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");
        menuGame.add(menuExit);

        Component[] buttons = buttonsPanel.getComponents();
        JButton p1 = (JButton) buttons[0];
        JButton p2 = (JButton) buttons[2];
        JButton start = (JButton) buttons[1];

        menuHumanHuman.addActionListener(e -> {
            player1 = "Human";
            player2 = "Human";
            p1.setText(player1);
            p2.setText(player2);
            start.setText("Start");
            start.doClick();
        });

        menuHumanRobot.addActionListener(e -> {
            player1 = "Human";
            player2 = "Robot";
            p1.setText(player1);
            p2.setText(player2);
            start.setText("Start");
            start.doClick();
        });

        menuRobotHuman.addActionListener(e -> {
            player1 = "Robot";
            player2 = "Human";
            p1.setText(player1);
            p2.setText(player2);
            start.setText("Start");
            start.doClick();
        });

        menuRobotRobot.addActionListener(e -> {
            player1 = "Robot";
            player2 = "Robot";
            p1.setText(player1);
            p2.setText(player2);
            start.setText("Start");
            start.doClick();
        });

        menuExit.addActionListener(e -> System.exit(0));
    }

    private void addControlButtons(JPanel buttonsPanel, JLabel labelStatus) {
        JButton buttonPlayer1 = new ControlJButton(player1);
        buttonPlayer1.setName("ButtonPlayer1");
        buttonsPanel.add(buttonPlayer1);

        JButton buttonStartReset = new ControlJButton("Start");
        buttonStartReset.setName("ButtonStartReset");
        buttonStartReset.setForeground(Color.WHITE);
        buttonStartReset.setBackground(Color.BLACK);
        buttonsPanel.add(buttonStartReset);

        JButton buttonPlayer2 = new ControlJButton(player2);
        buttonPlayer2.setName("ButtonPlayer2");
        buttonsPanel.add(buttonPlayer2);

        buttonStartReset.addActionListener(e -> {
            String buttonText = buttonStartReset.getText();
            gameOver = false;
            turnPlayer = "X";
            clearButtons();
            if (buttonText.equals("Start")) {
                buttonStartReset.setText("Reset");
                status = Status.IN_PROGRESS;
                labelStatus.setText(getStatusToLabel());
                unblockCells();
                buttonPlayer1.setEnabled(false);
                buttonPlayer2.setEnabled(false);
                startGame();
            } else {
                buttonPlayer1.setEnabled(true);
                buttonPlayer2.setEnabled(true);
                buttonStartReset.setText("Start");
                status = Status.NOT_STARTED;
                labelStatus.setText(String.valueOf(status));
            }
        });

        buttonPlayer1.addActionListener(e -> {
            player1 = player1.equals("Human") ? "Robot" : "Human";
            buttonPlayer1.setText(player1);
        });

        buttonPlayer2.addActionListener(e -> {
            player2 = player2.equals("Human") ? "Robot" : "Human";
            buttonPlayer2.setText(player2);
        });
    }

    private void startGame() {
        if (player1.equals("Robot")) {
            robotMove();
        }
    }

    private void robotMove() {
        if ((player1.equals("Robot") && turnPlayer.equals("X"))
            || (player2.equals("Robot") && turnPlayer.equals("O"))) {
            Random random = new Random();
            JButton chosenButton = flattenedButtons[random.nextInt(0, 9)];
            while (!chosenButton.getText().isBlank()) {
                chosenButton = flattenedButtons[random.nextInt(0, 9)];
            }
            chosenButton.doClick(300);
        }

    }

    private void clearButtons() {
        for (JButton[] row: buttons) {
            for (JButton button: row) {
                button.setText(" ");
            }
        }
    }

    private void addButtons(JPanel board, JLabel labelStatus) {
        for (int i = ROWS - 1; i >= 0; i--) {
            for (int j = 'A'; j < 'A' + COLUMNS; j++) {
                String buttonName = "Button" + Character.toString(j) + (i + 1);
                JButton jButton = new CustomJButton(buttonName);
                board.add(jButton);
                buttons[i][j - 'A'] = jButton;

                jButton.addActionListener(e -> {
                    if (jButton.getText().isBlank() && !gameOver) {
                        status = Status.IN_PROGRESS;
                        labelStatus.setText(getStatusToLabel());
                        jButton.setText(turnPlayer);
                        gameOver = checkStatus();
                        if (gameOver) {
                            if (!status.toString().equals("Draw")) {
                                status = Status.getValue(turnPlayer + " wins");
                            }
                            labelStatus.setText(getStatusToLabel());
                            blockCells();
                        } else {
                            turnPlayer = turnPlayer.equals("X") ? "O" : "X";
                            labelStatus.setText(getStatusToLabel());
                            robotMove();
                        }
                    }
                });
            }
        }
        blockCells();
        fillFlattenedButtons();
    }

    private String getStatusToLabel() {
        if (status == Status.IN_PROGRESS) {
            return String.format("The turn of %s Player (%s)", turnPlayer.equals("X") ? player1 : player2, turnPlayer);
        }
        if (status != Status.DRAW) {
            return String.format("The %s Player (%s) wins", turnPlayer.equals("X") ? player1 : player2, turnPlayer);
        }
        return "Draw";
    }

    private void fillFlattenedButtons() {
        int i = 0;
        for (JButton[] row: buttons) {
            for (JButton button: row) {
                flattenedButtons[i] = button;
                i++;
            }
        }
    }

    private void blockCells() {
        for (JButton[] row: buttons) {
            for (JButton button: row) {
                button.setEnabled(false);
            }
        }
    }

    private void unblockCells() {
        for (JButton[] row: buttons) {
            for (JButton button: row) {
                button.setEnabled(true);
            }
        }
    }

    private boolean checkStatus() {
        // row and column 0
        if (!buttons[0][0].getText().isBlank()) {
            String player = buttons[0][0].getText();
            // horizontal
            if (buttons[0][1].getText().equals(player) && buttons[0][2].getText().equals(player)) {
                return true;
            }
            // vertical
            if (buttons[1][0].getText().equals(player) && buttons[2][0].getText().equals(player)) {
                return true;
            }
        }

        // row and column 1
        if (!buttons[1][1].getText().isBlank()) {
            String player = buttons[1][1].getText();
            // horizontal
            if (buttons[1][0].getText().equals(player) && buttons[1][2].getText().equals(player)) {
                return true;
            }
            // vertical
            if (buttons[0][1].getText().equals(player) && buttons[2][1].getText().equals(player)) {
                return true;
            }
            // first diagonal
            if (buttons[0][0].getText().equals(player) && buttons[2][2].getText().equals(player)) {
                return true;
            }
            // second diagonal
            if (buttons[2][0].getText().equals(player) && buttons[0][2].getText().equals(player)) {
                return true;
            }
        }

        // row and column 2
        if (!buttons[2][2].getText().isBlank()) {
            String player = buttons[2][2].getText();
            // horizontal
            if (buttons[2][0].getText().equals(player) && buttons[2][1].getText().equals(player)) {
                return true;
            }
            // vertical
            if (buttons[0][2].getText().equals(player) && buttons[1][2].getText().equals(player)) {
                return true;
            }
        }

        return isDraw();
    }

    private boolean isDraw() {
        for (JButton[] row: buttons) {
            for (JButton button: row) {
                if (button.getText().isBlank()) {
                    return false;
                }
            }
        }
        status = Status.DRAW;
        return true;
    }
}

