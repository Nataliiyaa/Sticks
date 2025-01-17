package game;

import java.util.*;

public class GameLogicMediator implements GameMediator {
    private final GameBoard board;
    private final GameUI ui;

    public GameLogicMediator(GameBoard board, GameUI ui) {
        this.board = board;
        this.ui = ui;
    }

    public GameBoard getBoard() {
        return board;
    }

    @Override
    public boolean makeMove(int row, int col, boolean isHorizontal, Player player) {
        SquareState squareState = new SquareState();
        squareState.setOwner(player.getSymbol());

        if (board.drawEdge(row, col, isHorizontal)) {
            int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, squareState);

            ui.displayBoard(board);

            if (completedSquares > 0) {
                ui.displayMessage("Игрок " + player.getSymbol() + " завершил " + completedSquares + " квадрат(ов)! Дополнительный ход.");
                return board.isFull(); // Игрок делает ещё один ход, если поле не заполнено
            }
            return true; // Ход передаётся следующему игроку
        }

        if (player instanceof HumanPlayer) {
            ui.displayMessage("Неверный ход. Попробуйте снова.");
        }
        return false; // Ход не выполнен
    }

    @Override
    public int getBoardRows() {
        return board.getRows();
    }

    @Override
    public int getBoardCols() {
        return board.getCols();
    }

    /**
     * Подсчёт очков для игроков.
     */
    public void calculateAndDisplayScores(Player player1, Player player2) {
        int scorePlayer1 = 0;
        int scorePlayer2 = 0;

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                PlayerSymbol owner = board.getSquareState(i, j).getOwner();
                if (owner == player1.getSymbol()) {
                    scorePlayer1++;
                } else if (owner == player2.getSymbol()) {
                    scorePlayer2++;
                }
            }
        }

        ui.displayMessage("Результаты:");
        ui.displayMessage("Игрок " + player1.getSymbol() + ": " + scorePlayer1 + " очков");
        ui.displayMessage("Игрок " + player2.getSymbol() + ": " + scorePlayer2 + " очков");

        if (scorePlayer1 > scorePlayer2) {
            ui.displayMessage("Победил игрок " + player1.getSymbol() + "!");
        } else if (scorePlayer2 > scorePlayer1) {
            ui.displayMessage("Победил игрок " + player2.getSymbol() + "!");
        } else {
            ui.displayMessage("Ничья!");
        }
    }
}
