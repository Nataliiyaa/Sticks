package game;

import java.util.Scanner;
import javax.swing.*;


public class DotsAndSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            System.out.println("Добро пожаловать в игру 'Точки и квадраты'!");
            int rows, cols;
            while (true) {
                System.out.print("Введите количество строк и столбцов игрового поля через пробел (минимум 2x2): ");
                String[] dimensions = scanner.nextLine().split(" ");
                try {
                    rows = Integer.parseInt(dimensions[0]);
                    cols = Integer.parseInt(dimensions[1]);
                } catch (Exception e) {
                    System.out.println("Ошибка ввода. Убедитесь, что вы ввели два числа через пробел.");
                    continue;
                }
                if (rows >= 2 && cols >= 2) {
                    break;
                } else {
                    System.out.println("Размеры игрового поля должны быть не менее 2x2. Попробуйте снова.");
                }
            }

            GameBoard board = new GameBoard(rows, cols);

            System.out.println("Выберите режим игры:");
            System.out.println("1. Человек против человека");
            System.out.println("2. Человек против бота");
            int mode = scanner.nextInt();

            Player player1 = new HumanPlayer('X', scanner);
            Player player2 = (mode == 1) ? new HumanPlayer('O', scanner) : new BotPlayer('O');

            Player currentPlayer = player1;

            while (!board.isFull()) {
                board.printBoard();
                System.out.println("Ход игрока " + currentPlayer.getSymbol());
                if (currentPlayer.makeMove(board)) {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            }

            board.printBoard();
            System.out.println("Игра завершена!");
            System.out.println("Подсчёт очков...");

            int scorePlayer1 = 0;
            int scorePlayer2 = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (board.getSquareOwner(i, j) == player1.getSymbol()) {
                        scorePlayer1++;
                    } else if (board.getSquareOwner(i, j) == player2.getSymbol()) {
                        scorePlayer2++;
                    }
                }
            }

            System.out.println("Очки игрока X: " + scorePlayer1);
            System.out.println("Очки игрока O: " + scorePlayer2);

            if (scorePlayer1 > scorePlayer2) {
                System.out.println("Победил игрок X!");
            } else if (scorePlayer2 > scorePlayer1) {
                System.out.println("Победил игрок O!");
            } else {
                System.out.println("Ничья!");
            }

            System.out.println("Хотите сыграть ещё раз? (1 - Да, 0 - Нет): ");
            playAgain = scanner.nextInt() == 1;

        } while (playAgain);

        System.out.println("Спасибо за игру!");
    }
}
