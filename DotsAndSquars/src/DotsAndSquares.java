import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// Класс для представления игрового поля
class GameBoard {
    private final int size;
    private final boolean[][] horizontalEdges;
    private final boolean[][] verticalEdges;
    private final char[][] squares;

    public GameBoard(int size) {
        this.size = size;
        this.horizontalEdges = new boolean[size + 1][size];
        this.verticalEdges = new boolean[size][size + 1];
        this.squares = new char[size][size];
    }

    public boolean drawEdge(int row, int col, boolean isHorizontal) {
        if (isHorizontal) {
            if (row < 0 || row > size || col < 0 || col >= size || horizontalEdges[row][col]) {
                return false;
            }
            horizontalEdges[row][col] = true;
        } else {
            if (row < 0 || row >= size || col < 0 || col > size || verticalEdges[row][col]) {
                return false;
            }
            verticalEdges[row][col] = true;
        }
        return true;
    }

    public int checkAndMarkSquares(int row, int col, boolean isHorizontal, char playerSymbol) {
        int completedSquares = 0;
        if (isHorizontal) {
            if (row < size && col < size && isSquareComplete(row, col)) {
                squares[row][col] = playerSymbol;
                completedSquares++;
            }
            if (row > 0 && col < size && isSquareComplete(row - 1, col)) {
                squares[row - 1][col] = playerSymbol;
                completedSquares++;
            }
        } else {
            if (col < size && row < size && isSquareComplete(row, col)) {
                squares[row][col] = playerSymbol;
                completedSquares++;
            }
            if (col > 0 && row < size && isSquareComplete(row, col - 1)) {
                squares[row][col - 1] = playerSymbol;
                completedSquares++;
            }
        }
        return completedSquares;
    }

    private boolean isSquareComplete(int row, int col) {
        return horizontalEdges[row][col] && horizontalEdges[row + 1][col]
                && verticalEdges[row][col] && verticalEdges[row][col + 1];
    }

    public boolean isFull() {
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j < size; j++) {
                if (i < size && !horizontalEdges[i][j]) {
                    return false;
                }
                if (j < size && !verticalEdges[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("+" + (i <= size && horizontalEdges[i][j] ? "---" : "   "));
            }
            System.out.println("+");
            if (i < size) {
                for (int j = 0; j <= size; j++) {
                    System.out.print((j <= size && verticalEdges[i][j] ? "|" : " ") + " " + (j < size && squares[i][j] != '\u0000' ? squares[i][j] : " ") + " ");
                }
                System.out.println();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public char getSquareOwner(int row, int col) {
        return squares[row][col];
    }
}

// Класс для игрока
abstract class Player {
    protected final char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public abstract boolean makeMove(GameBoard board);
}

// Реализация человека-игрока
class HumanPlayer extends Player {
    private final Scanner scanner;

    public HumanPlayer(char symbol, Scanner scanner) {
        super(symbol);
        this.scanner = scanner;
    }

    @Override
    public boolean makeMove(GameBoard board) {
        while (true) {
            try {
                System.out.println("Введите ход (строка, столбец, горизонтально [1 - да, 0 - нет]): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                int horizontalInput = scanner.nextInt();

                if (horizontalInput != 0 && horizontalInput != 1) {
                    System.out.println("Неверное значение для горизонтально. Введите 1 (да) или 0 (нет).");
                    continue;
                }

                boolean isHorizontal = horizontalInput == 1;

                if (board.drawEdge(row, col, isHorizontal)) {
                    int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                    if (completedSquares > 0) {
                        System.out.println("Вы завершили " + completedSquares + " квадрат(ов)! Сделайте ещё ход.");
                        return false;
                    }
                    return true;
                } else {
                    System.out.println("Неверный ход. Попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите корректные данные.");
                scanner.nextLine(); // Очистка ввода
            }
        }
    }
}

// Реализация бота
class BotPlayer extends Player {
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
            if (board.drawEdge(row, col, isHorizontal)) {
                int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                if (completedSquares > 0) {
                    System.out.println("Бот завершил " + completedSquares + " квадрат(ов)!");
                    return false;
                }
                return true;
            }
        }
    }
}

// Основной класс игры
public class DotsAndSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
    }
}
