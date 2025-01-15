package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner scanner;

    public HumanPlayer(char symbol, Scanner scanner) {
        super(symbol);
        this.scanner = scanner;
    }

    @Override
    public boolean makeMove(GameBoard board) {
        while (true) {
            try {
                System.out.println("Введите ход (строка, столбец, горизонтально [1 - да, 0 - нет], разделённые пробелом): ");
                String input = scanner.nextLine();
                String[] parts = input.trim().split("\\s+");

                if (parts.length != 3) {
                    System.out.println("Ошибка ввода. Убедитесь, что вы ввели три значения, разделённые пробелами.");
                    continue;
                }

                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                int horizontalInput = Integer.parseInt(parts[2]);

                if (horizontalInput != 0 && horizontalInput != 1) {
                    System.out.println("Неверное значение для горизонтально. Введите 1 (да) или 0 (нет).");
                    continue;
                }

                boolean isHorizontal = horizontalInput == 1;

                // Пробуем выполнить ход
                if (board.drawEdge(row, col, isHorizontal)) {
                    int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                    if (completedSquares > 0) {
                        System.out.println("Вы завершили " + completedSquares + " квадрат(ов)! Сделайте ещё ход.");
                        return false; // Игрок делает ещё один ход
                    }
                    return true; // Ход передаётся следующему игроку
                } else {
                    System.out.println("Неверный ход. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите три целых числа, разделённых пробелами.");
            }
        }
    }
}
