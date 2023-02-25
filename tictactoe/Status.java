package tictactoe;

public enum Status {
    NOT_STARTED("Game is not started"),
    IN_PROGRESS("Game in progress"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    DRAW("Draw");

    private String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Status getValue(String statusValue) {
        for (Status s: values()) {
            if (s.toString().equals(statusValue)) {
                return s;
            }
        }
        return NOT_STARTED;
    }
}
