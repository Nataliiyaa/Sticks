package game;

import java.util.Scanner;

public class DotsAndSquares {
    public static void main(String[] args) {
        GameUI ui;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип интерфейса:");
        System.out.println("1. Консольный");
        System.out.println("2. Оконный");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Считываем остаток строки

        if (choice == 1) {
            ui = new ConsoleUI();
        } else {
            ui = new SwingUI();
        }

        boolean playAgain;

        do {
            ui.displayMessage("Добро пожаловать в игру 'Точки и квадраты'!");

            int rows = Integer.parseInt(ui.getUserInput("Введите количество строк (минимум 2): "));
            int cols = Integer.parseInt(ui.getUserInput("Введите количество столбцов (минимум 2): "));

            GameBoard board = new GameBoard(rows, cols);
            GameMediator mediator = new GameLogicMediator(board, ui);

            ui.displayMessage("Выберите режим игры:\n1. Человек против человека\n2. Человек против бота");
            int mode = Integer.parseInt(ui.getUserInput("Ваш выбор: "));

            Player player1 = new HumanPlayer(PlayerSymbol.X, ui);
            Player player2 = (mode == 1) ? new HumanPlayer(PlayerSymbol.O, ui) : new BotPlayer(PlayerSymbol.O);

            Player currentPlayer = player1;

            ui.displayBoard(board);
            while (true) {
                ui.displayMessage("Ход игрока " + currentPlayer.getSymbol());
                boolean mustSwitchTurn = currentPlayer.makeMove(mediator);

                if (board.isFull()) {
                    break;
                }

                if (mustSwitchTurn) {
                    currentPlayer = (currentPlayer == player1) ? player2 : player1;
                }
            }

            ui.displayBoard(board);
            ui.displayMessage("Игра завершена!");
            System.out.println("Подсчёт очков...");

            mediator.calculateAndDisplayScores(player1, player2);

            playAgain = ui.askPlayAgain();

        } while (playAgain);

        ui.displayMessage("Спасибо за игру!");
    }
}
