package game;

import java.util.*;

public class GameLogicMediator implements GameMediator {
    private final GameBoard board;
    private final GameRenderer renderer;

    public GameLogicMediator(GameBoard board, GameRenderer renderer) {
        this.board = board;
        this.renderer = renderer;
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

            renderer.render(board);

            if (completedSquares > 0) {
                System.out.println("Игрок " + player.getSymbol() + " завершил " + completedSquares + " квадрат(ов)! Дополнительный ход.");
                if (board.isFull()) {
                    return true;
                }
                return false; // Игрок делает ещё один ход
            }
            return true; // Ход передаётся следующему игроку
        }

        if (player instanceof HumanPlayer) {
            System.out.println("Неверный ход. Попробуйте снова.");
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
}
