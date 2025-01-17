package game;

public class HumanPlayer extends Player {
    private final GameUI ui;

    public HumanPlayer(PlayerSymbol symbol, GameUI ui) {
        super(symbol);
        this.ui = ui;
    }

    @Override
    public boolean makeMove(GameMediator mediator) {
        while (true) {
            String input = ui.getUserInput("Введите ход (строка, столбец, горизонтально [1 - да, 0 - нет], разделённые пробелом): ");
            String[] parts = input.trim().split("\\s+");

            if (parts.length != 3) {
                ui.displayMessage("Ошибка ввода. Убедитесь, что вы ввели три значения, разделённые пробелами.");
                continue;
            }

            int row, col, horizontalInput;
            try {
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
                horizontalInput = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                ui.displayMessage("Ошибка ввода. Убедитесь, что все значения являются числами.");
                continue;
            }

            if (horizontalInput != 0 && horizontalInput != 1) {
                ui.displayMessage("Неверное значение для горизонтально. Введите 1 (да) или 0 (нет).");
                continue;
            }

            boolean isHorizontal = horizontalInput == 1;

            if (mediator.makeMove(row, col, isHorizontal, this)) {
                return true; // Ход выполнен, передаётся следующему игроку
            }
        }
    }
}
