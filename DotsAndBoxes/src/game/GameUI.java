package game;

public interface GameUI {
    void displayMessage(String message);
    void displayBoard(GameBoard board);
    String getUserInput(String prompt);
    boolean askPlayAgain();
}
