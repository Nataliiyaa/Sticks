package game;

import java.util.List;
import java.util.Random;

public class BotPlayer extends Player {
    private final Random random;

    public BotPlayer(PlayerSymbol symbol) {
        super(symbol);
        this.random = new Random();
    }

    @Override
    public boolean makeMove(GameMediator mediator) {
        int maxRows = mediator.getBoardRows(); // Общее число строк
        int maxCols = mediator.getBoardCols(); // Общее число столбцов

        while (true) {
            // Генерация случайных координат
            int row = random.nextInt(maxRows + 1); // +1 для горизонтальных линий
            int col = random.nextInt(maxCols + 1); // +1 для вертикальных линий
            boolean isHorizontal = random.nextBoolean(); // Определяем горизонтальную/вертикальную линию

            // Пытаемся сделать ход
            if (mediator.makeMove(row, col, isHorizontal, this)) {
                return true; // Успешный ход, передаём ход следующему игроку
            }

            // Если ход не успешен, повторяем генерацию
        }
    }
}