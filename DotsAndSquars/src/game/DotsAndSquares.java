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
                String[] dimensions = scanner.nextLine().split("\\s+");
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
            GameRenderer renderer = new ConsoleRenderer();
            GameMediator mediator = new GameLogicMediator(board, renderer);

            System.out.println("Выберите режим игры:");
            System.out.println("1. Человек против человека");
            System.out.println("2. Человек против бота");
            int mode = scanner.nextInt();
            scanner.nextLine(); // Считываем символ новой строки

            Player player1 = new HumanPlayer(PlayerSymbol.X, scanner);
            Player player2 = (mode == 1) ? new HumanPlayer(PlayerSymbol.O, scanner) : new BotPlayer(PlayerSymbol.O);

            Player currentPlayer = player1;

            renderer.render(board);
            while (true) {
                System.out.println("Ход игрока " + currentPlayer.getSymbol());
                boolean mustSwitchTurn = currentPlayer.makeMove(mediator);

                // Проверка завершения игры после каждого хода
                if (board.isFull()) {
                    break; // Игра завершена
                }

                // Если ход завершён, передать его следующему игроку
                if (mustSwitchTurn) {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            }

            renderer.render(board);
            System.out.println("Игра завершена!");
            System.out.println("Подсчёт очков...");

            int scorePlayer1 = 0;
            int scorePlayer2 = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (board.getSquareState(i, j).getOwner() == player1.getSymbol()) {
                        scorePlayer1++;
                    } else if (board.getSquareState(i, j).getOwner() == player2.getSymbol()) {
                        scorePlayer2++;
                    }
                }
            }

            System.out.println("Результаты:");
            System.out.println("Игрок " + player1.getSymbol() + ": " + scorePlayer1 + " очков");
            System.out.println("Игрок " + player2.getSymbol() + ": " + scorePlayer2 + " очков");

            if (scorePlayer1 > scorePlayer2) {
                System.out.println("Победил игрок " + player1.getSymbol() + "!");
            } else if (scorePlayer2 > scorePlayer1) {
                System.out.println("Победил игрок " + player2.getSymbol() + "!");
            } else {
                System.out.println("Ничья!");
            }

            System.out.print("Хотите сыграть ещё раз? (да/нет): ");
            String response = scanner.nextLine().trim().toLowerCase();
            playAgain = response.equals("да") || response.equals("yes");

        } while (playAgain);

        System.out.println("Спасибо за игру!");
        scanner.close();
    }
}
