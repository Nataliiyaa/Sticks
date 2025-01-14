package game;

import java.util.Scanner;

public class DotsAndSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            System.out.println("Добро пожаловать в игру 'Точки и квадраты'!");
            System.out.print("Введите размер игрового поля (например, 5 для 5x5): ");
            int size = scanner.nextInt();

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

            // Добавляем проверку ввода для повтора игры
            System.out.println("Хотите сыграть ещё раз? (1 - Да, 0 - Нет): ");
            while (true) {
                int input = scanner.nextInt();
                if (input == 1) {
                    playAgain = true;
                    break;
                } else if (input == 0) {
                    playAgain = false;
                    break;
                } else {
                    System.out.println("Неверный ввод. Введите 1 (Да) или 0 (Нет): ");
                }
            }

        } while (playAgain);

        System.out.println("Спасибо за игру!");
    }
}
