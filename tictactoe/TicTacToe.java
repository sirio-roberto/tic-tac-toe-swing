package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TicTacToe extends JFrame {
    private final int SIDE = 3;

    private String turnPlayer;

    private boolean gameOver = false;

    private Status status;

    private final JButton[][] cells = new JButton[SIDE][SIDE];
    private JButton buttonPlayer1;
    private JButton buttonPlayer2;
    private JButton buttonStartReset;

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
        board.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

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
        addMenusToBar(menuBar);

        setVisible(true);
    }

    private String getPlayer1() {
        return buttonPlayer1.getText();
    }

    private String getPlayer2() {
        return buttonPlayer2.getText();
    }

    private String getOppositePlayer(String playerName) {
        return "Human".equals(playerName) ? "Robot" : "Human";
    }

    private void addMenusToBar(JMenuBar menuBar) {
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

        menuHumanHuman.addActionListener(e -> {
            if (!"Human".equals(getPlayer1())) {
                buttonPlayer1.doClick();
            }
            if (!"Human".equals(getPlayer2())) {
                buttonPlayer2.doClick();
            }
            buttonStartReset.setText("Start");
            buttonStartReset.doClick();
        });

        menuHumanRobot.addActionListener(e -> {
            if (!"Human".equals(getPlayer1())) {
                buttonPlayer1.doClick();
            }
            if (!"Robot".equals(getPlayer2())) {
                buttonPlayer2.doClick();
            }
            buttonStartReset.setText("Start");
            buttonStartReset.doClick();
        });

        menuRobotHuman.addActionListener(e -> {
            if (!"Robot".equals(getPlayer1())) {
                buttonPlayer1.doClick();
            }
            if (!"Human".equals(getPlayer2())) {
                buttonPlayer2.doClick();
            }
            buttonStartReset.setText("Start");
            buttonStartReset.doClick();
        });

        menuRobotRobot.addActionListener(e -> {
            if (!"Robot".equals(getPlayer1())) {
                buttonPlayer1.doClick();
            }
            if (!"Robot".equals(getPlayer2())) {
                buttonPlayer2.doClick();
            }
            buttonStartReset.setText("Start");
            buttonStartReset.doClick();
        });

        menuExit.addActionListener(e -> System.exit(0));
    }

    private void addControlButtons(JPanel buttonsPanel, JLabel labelStatus) {
        buttonPlayer1 = new ControlJButton("Human");
        buttonPlayer1.setName("ButtonPlayer1");
        buttonsPanel.add(buttonPlayer1);

        buttonStartReset = new ControlJButton("Start");
        buttonStartReset.setName("ButtonStartReset");
        buttonStartReset.setForeground(Color.WHITE);
        buttonStartReset.setBackground(Color.BLACK);
        buttonsPanel.add(buttonStartReset);

        buttonPlayer2 = new ControlJButton("Human");
        buttonPlayer2.setName("ButtonPlayer2");
        buttonsPanel.add(buttonPlayer2);

        buttonStartReset.addActionListener(e -> {
            String buttonText = buttonStartReset.getText();
            gameOver = false;
            turnPlayer = "X";
            clearButtons();
            if (buttonText.equals("Start")) {
                buttonStartReset.setText("Reset");
                buttonPlayer1.setEnabled(false);
                buttonPlayer2.setEnabled(false);
                status = Status.IN_PROGRESS;
                labelStatus.setText(getStatusToLabel());
                unblockCells();
                startGame();
            } else {
                buttonStartReset.setText("Start");
                buttonPlayer1.setEnabled(true);
                buttonPlayer2.setEnabled(true);
                status = Status.NOT_STARTED;
                labelStatus.setText(String.valueOf(status));
            }
        });

        buttonPlayer1.addActionListener(e -> buttonPlayer1.setText(getOppositePlayer(buttonPlayer1.getText())));

        buttonPlayer2.addActionListener(e -> buttonPlayer2.setText(getOppositePlayer(buttonPlayer2.getText())));
    }

    private void startGame() {
        if ("Robot".equals(getPlayer1())) {
            robotMove();
        }
    }

    private void robotMove() {
        if (("Robot".equals(getPlayer1()) && "X".equals(turnPlayer))
            || ("Robot".equals(getPlayer2()) && "O".equals(turnPlayer))) {
            Random random = new Random();
            int i = random.nextInt(0, 3);
            int j = random.nextInt(0, 3);
            JButton chosenButton = cells[i][j];
            while (!chosenButton.getText().isBlank()) {
                i = random.nextInt(0, 3);
                j = random.nextInt(0, 3);
                chosenButton = cells[i][j];
            }
            Timer timer = new Timer();
            JButton finalChosenButton = chosenButton;
            TimerTask move = new TimerTask() {
                @Override
                public void run() {
                    finalChosenButton.doClick();
                }
            };
            timer.schedule(move, 800);
        }
    }

    private void clearButtons() {
        for (JButton[] row: cells) {
            for (JButton button: row) {
                button.setText(" ");
            }
        }
    }

    private void addButtons(JPanel board, JLabel labelStatus) {
        for (int i = SIDE - 1; i >= 0; i--) {
            for (int j = 'A'; j < 'A' + SIDE; j++) {
                String buttonName = "Button" + Character.toString(j) + (i + 1);
                JButton jButton = new CustomJButton(buttonName);
                jButton.setEnabled(false);
                board.add(jButton);
                cells[i][j - 'A'] = jButton;

                jButton.addActionListener(e -> {
                    if (jButton.getText().isBlank() && !gameOver) {
                        jButton.setText(turnPlayer);
                        gameOver = checkStatus();
                        if (gameOver) {
                            if (!status.toString().equals("Draw")) {
                                status = Status.getValue(turnPlayer + " wins");
                            }
                            labelStatus.setText(getStatusToLabel());
                            blockCells();
                        } else {
                            turnPlayer = "X".equals(turnPlayer) ? "O" : "X";
                            labelStatus.setText(getStatusToLabel());
                            robotMove();
                        }
                    }
                });
            }
        }
    }

    private String getStatusToLabel() {
        if (status == Status.IN_PROGRESS) {
            return String.format("The turn of %s Player (%s)",
                    turnPlayer.equals("X") ? getPlayer1() : getPlayer2(),
                    turnPlayer);
        }
        if (status != Status.DRAW) {
            return String.format("The %s Player (%s) wins",
                    turnPlayer.equals("X") ? getPlayer1() : getPlayer2(),
                    turnPlayer);
        }
        return "Draw";
    }

    private void blockCells() {
        for (JButton[] row: cells) {
            for (JButton button: row) {
                button.setEnabled(false);
            }
        }
    }

    private void unblockCells() {
        for (JButton[] row: cells) {
            for (JButton button: row) {
                button.setEnabled(true);
            }
        }
    }

    private boolean checkStatus() {
        // row and column 0
        if (!cells[0][0].getText().isBlank()) {
            String player = cells[0][0].getText();
            // horizontal
            if (cells[0][1].getText().equals(player) && cells[0][2].getText().equals(player)) {
                return true;
            }
            // vertical
            if (cells[1][0].getText().equals(player) && cells[2][0].getText().equals(player)) {
                return true;
            }
        }

        // row and column 1
        if (!cells[1][1].getText().isBlank()) {
            String player = cells[1][1].getText();
            // horizontal
            if (cells[1][0].getText().equals(player) && cells[1][2].getText().equals(player)) {
                return true;
            }
            // vertical
            if (cells[0][1].getText().equals(player) && cells[2][1].getText().equals(player)) {
                return true;
            }
            // first diagonal
            if (cells[0][0].getText().equals(player) && cells[2][2].getText().equals(player)) {
                return true;
            }
            // second diagonal
            if (cells[2][0].getText().equals(player) && cells[0][2].getText().equals(player)) {
                return true;
            }
        }

        // row and column 2
        if (!cells[2][2].getText().isBlank()) {
            String player = cells[2][2].getText();
            // horizontal
            if (cells[2][0].getText().equals(player) && cells[2][1].getText().equals(player)) {
                return true;
            }
            // vertical
            if (cells[0][2].getText().equals(player) && cells[1][2].getText().equals(player)) {
                return true;
            }
        }

        return isDraw();
    }

    private boolean isDraw() {
        for (JButton[] row: cells) {
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

