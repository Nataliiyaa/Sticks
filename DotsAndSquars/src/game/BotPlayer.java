package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    private final Random random;

    public BotPlayer(char symbol) {
        super(symbol);
        this.random = new Random();
    }

    @Override
    public boolean makeMove(GameBoard board) {
        while (true) {
            List<Move> availableMoves = getAvailableMoves(board);

            if (availableMoves.isEmpty()) {
                System.out.println("Нет доступных ходов для бота.");
                return true; // Завершить ход, если нет доступных ходов
            }

            int randomIndex = random.nextInt(availableMoves.size());
            Move move = availableMoves.get(randomIndex);

            if (board.drawEdge(move.row, move.col, move.isHorizontal)) {
                int completedSquares = board.checkAndMarkSquares(move.row, move.col, move.isHorizontal, symbol);
                if (completedSquares > 0) {
                    System.out.println("Бот завершил " + completedSquares + " квадрат(ов)! Он сделает ещё ход.");
                    // Если квадрат завершён, продолжить цикл (ход бота)
                } else {
                    return true; // Если квадратов не завершено, ход переходит игроку
                }
            }
        }
    }

    private List<Move> getAvailableMoves(GameBoard board) {
        List<Move> availableMoves = new ArrayList<>();

        // Проходим по всем возможным рёбрам
        for (int row = 0; row <= board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                // Проверяем горизонтальные рёбра
                if (row <= board.getRows() && !board.getHorizontalEdges()[row][col]) {
                    availableMoves.add(new Move(row, col, true));
                }
            }
        }

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col <= board.getCols(); col++) {
                // Проверяем вертикальные рёбра
                if (col <= board.getCols() && !board.getVerticalEdges()[row][col]) {
                    availableMoves.add(new Move(row, col, false));
                }
            }
        }

        return availableMoves;
    }

    private static class Move {
        int row, col;
        boolean isHorizontal;

        Move(int row, int col, boolean isHorizontal) {
            this.row = row;
            this.col = col;
            this.isHorizontal = isHorizontal;
        }
    }
}