package game;

import java.util.Scanner;
import javax.swing.*;


public class DotsAndSquares {
    public static void main(String[] args) {
//        // Выбор интерфейса
//        String[] options = {"Консоль", "Оконный интерфейс"};
//        int choice = JOptionPane.showOptionDialog(
//                null,
//                "Как вы хотите играть?",
//                "Выбор интерфейса",
//                JOptionPane.DEFAULT_OPTION,
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                options,
//                options[0]
//        );
//
//        if (choice == 0) {
//            // Консольная версия
//            playInConsole();
//        } else if (choice == 1) {
//            // Графическая версия
//            SwingUtilities.invokeLater(() -> new GameWindow().start());
//        } else {
//            System.out.println("Игра завершена.");
//        }
//    }
//
//    private static void playInConsole() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            System.out.println("Добро пожаловать в игру 'Точки и квадраты'!");
            int size;
            while (true) {
                System.out.print("Введите размер игрового поля (например, 5 для 5x5, минимум 2): ");
                size = scanner.nextInt();
                if (size >= 2) {
                    break; // Выходим из цикла, если размер корректен
                } else {
                    System.out.println("Размер игрового поля должен быть не менее 2. Попробуйте снова.");
                }
            }

            GameBoard board = new GameBoard(size);

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
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
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
