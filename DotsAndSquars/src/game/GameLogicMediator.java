package game;

import java.util.*;

public class GameLogicMediator implements GameMediator {
    private final GameBoard board;
    private final GameRenderer renderer;

    public GameLogicMediator(GameBoard board, GameRenderer renderer) {
        this.board = board;
        this.renderer = renderer;
    }

    @Override
    public boolean makeMove(int row, int col, boolean isHorizontal, Player player) {
        if (board.drawEdge(row, col, isHorizontal)) {
            int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, player.getSymbol());

            // Перерисовать поле после хода
            renderer.render(board);

            if (completedSquares > 0) {
                System.out.println("Игрок " + player.getSymbol() + " завершил " + completedSquares + " квадрат(ов)!");
                return false; // Игрок делает ещё один ход
            }
            return true; // Ход передаётся следующему игроку
        } else {
            System.out.println("Неверный ход. Попробуйте снова.");
            return false; // Ход не выполнен
        }
    }

    public List<Move> getAvailableMoves() {
        List<Move> availableMoves = new ArrayList<>();

        for (int row = 0; row <= board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                if (row <= board.getRows() && !board.getHorizontalEdges()[row][col]) {
                    availableMoves.add(new Move(row, col, true));
                }
            }
        }

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getCols(); col++) {
                if (col <= board.getCols() && !board.getVerticalEdges()[row][col]) {
                    availableMoves.add(new Move(row, col, false));
                }
            }
        }

        return availableMoves;
    }

    static class Move {
        int row, col;
        boolean isHorizontal;

        Move(int row, int col, boolean isHorizontal) {
            this.row = row;
            this.col = col;
            this.isHorizontal = isHorizontal;
        }
    }
}
