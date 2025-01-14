import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

// Класс для представления игрового поля
class GameBoard {
    private final int size;
    public final boolean[][] horizontalEdges;
    public final boolean[][] verticalEdges;
    private final char[][] squares;

    public GameBoard(int size) {
        this.size = size;
        this.horizontalEdges = new boolean[size + 1][size];
        this.verticalEdges = new boolean[size][size + 1];
        this.squares = new char[size][size];
    }

    public boolean drawEdge(int row, int col, boolean isHorizontal) {
        if (row == size && col == size) {
            return false; // Нельзя рисовать в правой нижней точке
        }

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
                System.out.print("+" + (i < size && horizontalEdges[i][j] ? "---" : "   "));
            }
            System.out.println("+");
            if (i < size) {
                for (int j = 0; j <= size; j++) {
                    System.out.print((j < size && verticalEdges[i][j] ? "|" : " ") + " " + (j < size && squares[i][j] != '\u0000' ? squares[i][j] : " ") + " ");
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
        int size = board.getSize();
        while(true) {
            System.out.println("Введите ход (строка, столбец, горизонтально [1 - да, 0 - нет]): ");
            try {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                int isHorizontalInt = scanner.nextInt();
                if (row < 0 || row > size || col < 0 || col >= size || (isHorizontalInt != 0 && isHorizontalInt != 1)) {
                    System.out.println("Неверный ход. Попробуйте снова.");
                    continue; // Пропускаем остаток итерации и просим ввести заново
                }
                boolean isHorizontal = isHorizontalInt == 1;

                if (!board.drawEdge(row, col, isHorizontal)) {
                    System.out.println("Неверный ход. Попробуйте снова.");
                    continue; // Пропускаем остаток итерации и просим ввести заново
                }
                int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                if (completedSquares > 0) {
                    System.out.println("Вы завершили " + completedSquares + " квадрат(ов)! Сделайте ещё ход.");
                    return false; // Оставляем ход у текущего игрока
                }
                return true;

            } catch (InputMismatchException e) {
                System.out.println("Неверный формат ввода. Попробуйте снова.");
                scanner.next(); // Очистить некорректный ввод
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

        // 1. Поиск хода, завершающего квадрат
        for (int row = 0; row <= size; row++) {
            for (int col = 0; col < size; col++) {
                for (int direction = 0; direction < 2; direction++) {
                    boolean isHorizontal = direction == 1;
                    if (canDrawEdge(board, row, col, isHorizontal)) {
                        if (checkCompleteSquare(board, row, col, isHorizontal)) {
                            if (board.drawEdge(row, col, isHorizontal)) {
                                board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                                System.out.println("Бот завершил квадрат!");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // 2. Если нет завершающего хода, сделать случайный
        while (true) {
            int row = random.nextInt(size + 1);
            int col = random.nextInt(size);
            boolean isHorizontal = random.nextBoolean();
            if (board.drawEdge(row, col, isHorizontal)) {
                int completedSquares = board.checkAndMarkSquares(row, col, isHorizontal, symbol);
                if (completedSquares > 0) {
                    System.out.println("Бот завершил " + completedSquares + " квадрат(ов)!");
                    return false; // Оставляем ход у бота
                }
                return true;
            }
        }
    }

    private boolean checkCompleteSquare(GameBoard board, int row, int col, boolean isHorizontal){
        if (isHorizontal) {
            if (row < board.getSize() && col < board.getSize() && isSquareCompleteBot(board, row, col)) {
                return true;
            }
            if (row > 0 && col < board.getSize() && isSquareCompleteBot(board, row - 1, col)) {
                return true;
            }
        } else {
            if (col < board.getSize() && row < board.getSize() && isSquareCompleteBot(board, row, col)) {
                return true;
            }
            if (col > 0 && row < board.getSize() && isSquareCompleteBot(board, row, col - 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean canDrawEdge(GameBoard board, int row, int col, boolean isHorizontal){
        if (row == board.getSize() && col == board.getSize()) {
            return false; // Нельзя рисовать в правой нижней точке
        }

        if (isHorizontal) {
            if (row < 0 || row > board.getSize() || col < 0 || col >= board.getSize() || board.horizontalEdges[row][col]) {
                return false;
            }
        } else {
            if (row < 0 || row >= board.getSize() || col < 0 || col > board.getSize() || board.verticalEdges[row][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean isSquareCompleteBot(GameBoard board, int row, int col) {
        return board.horizontalEdges[row][col] && board.horizontalEdges[row + 1][col]
                && board.verticalEdges[row][col] && board.verticalEdges[row][col + 1];
    }
}

// Основной класс игры
public class DotsAndSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в игру 'Точки и квадраты'!");
        int size = 0;
        while (true) {
            System.out.print("Введите размер игрового поля (например, 5 для 5x5): ");
            try {
                size = scanner.nextInt();
                if (size <= 1) {
                    System.out.println("Размер поля должен быть больше 1.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Неверный формат ввода. Попробуйте снова.");
                scanner.next();
            }
        }


        GameBoard board = new GameBoard(size);

        int mode = 0;
        while(true) {
            System.out.println("Выберите режим игры:");
            System.out.println("1. Человек против человека");
            System.out.println("2. Человек против бота");
            try{
                mode = scanner.nextInt();
                if (mode != 1 && mode != 2) {
                    System.out.println("Неверный выбор режима. Попробуйте снова.");
                    continue;
                }
                break;
            }catch(InputMismatchException e){
                System.out.println("Неверный формат ввода. Попробуйте снова.");
                scanner.next();
            }
        }


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