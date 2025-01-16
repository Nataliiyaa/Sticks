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
    public boolean makeMove(GameMediator mediator) {
        while (true) {
            List<GameLogicMediator.Move> availableMoves = ((GameLogicMediator) mediator).getAvailableMoves();

            if (availableMoves.isEmpty()) {
                System.out.println("Нет доступных ходов для бота.");
                return true; // Завершить ход, если нет доступных ходов
            }

            int randomIndex = random.nextInt(availableMoves.size());
            GameLogicMediator.Move move = availableMoves.get(randomIndex);

            if (mediator.makeMove(move.row, move.col, move.isHorizontal, this)) {
                return true; // Ход выполнен, передаётся следующему игроку
            }
        }
    }
}