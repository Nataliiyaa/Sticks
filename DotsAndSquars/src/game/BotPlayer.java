package game;

import java.util.Random;

public class BotPlayer extends Player {
    private final Random random;

    public BotPlayer(char symbol) {
        super(symbol);
        this.random = new Random();
    }

    @Override
    public boolean makeMove(GameBoard board) {
        int size = board.getSize();
        while (true) {
            int row = random.nextInt(size + 1);
            int col = random.nextInt(size);
            boolean isHorizontal = random.nextBoolean();

            // Пробуем выполнить ход на оригинальном игровом поле
            if (board.drawEdge(row, col, isHorizontal)) {
                int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                if (completedSquares > 0) {
                    System.out.println("Бот завершил " + completedSquares + " квадрат(ов)!");
                    return false; // Бот делает ещё один ход
                }
                return true; // Ход передаётся следующему игроку
            }
        }
    }
}


