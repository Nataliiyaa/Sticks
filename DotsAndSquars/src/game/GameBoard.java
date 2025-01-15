package game;

public class GameBoard {
    private final int rows;
    private final int cols;
    private final boolean[][] horizontalEdges;
    private final boolean[][] verticalEdges;
    private final char[][] squares;
    private int completedSquaresCount;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.horizontalEdges = new boolean[rows + 1][cols];
        this.verticalEdges = new boolean[rows][cols + 1];
        this.squares = new char[rows][cols];
        this.completedSquaresCount = 0;
    }

    public boolean[][] getHorizontalEdges() {
        return horizontalEdges;
    }

    public boolean[][] getVerticalEdges() {
        return verticalEdges;
    }

    public char[][] getSquares() {
        return squares;
    }

    public int getCompletedSquaresCount() {
        return completedSquaresCount;
    }

    public boolean drawEdge(int row, int col, boolean isHorizontal) {
        if (isHorizontal) {
            if (row < 0 || row > rows || col < 0 || col >= cols || horizontalEdges[row][col]) {
                return false;
            }
            horizontalEdges[row][col] = true;
        } else {
            if (row < 0 || row >= rows || col < 0 || col > cols || verticalEdges[row][col]) {
                return false;
            }
            verticalEdges[row][col] = true;
        }
        return true;
    }

    public int checkAndMarkSquares(int row, int col, boolean isHorizontal, char playerSymbol) {
        int completedSquares = 0;
        if (isHorizontal) {
            if (row < rows && col < cols && isSquareComplete(row, col)) {
                squares[row][col] = playerSymbol;
                completedSquares++;
            }
            if (row > 0 && col < cols && isSquareComplete(row - 1, col)) {
                squares[row - 1][col] = playerSymbol;
                completedSquares++;
            }
        } else {
            if (col < cols && row < rows && isSquareComplete(row, col)) {
                squares[row][col] = playerSymbol;
                completedSquares++;
            }
            if (col > 0 && row < rows && isSquareComplete(row, col - 1)) {
                squares[row][col - 1] = playerSymbol;
                completedSquares++;
            }
        }
        completedSquaresCount += completedSquares;
        return completedSquares;
    }

    private boolean isSquareComplete(int row, int col) {
        return horizontalEdges[row][col] && horizontalEdges[row + 1][col]
                && verticalEdges[row][col] && verticalEdges[row][col + 1];
    }

    public boolean isFull() {
        return completedSquaresCount == rows * cols;
    }

    public void printBoard() {
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("+" + (i <= rows && horizontalEdges[i][j] ? "---" : "   "));
            }
            System.out.println("+");
            if (i < rows) {
                for (int j = 0; j <= cols; j++) {
                    System.out.print((j <= cols && verticalEdges[i][j] ? "|" : " ") + " " + (j < cols && squares[i][j] != '\u0000' ? squares[i][j] : " ") + " ");
                }
                System.out.println();
            }
        }
    }

    public GameBoard copy() {
        GameBoard copy = new GameBoard(rows, cols);
        for (int i = 0; i <= rows; i++) {
            System.arraycopy(horizontalEdges[i], 0, copy.horizontalEdges[i], 0, cols);
        }
        for (int i = 0; i < rows; i++) {
            System.arraycopy(verticalEdges[i], 0, copy.verticalEdges[i], 0, cols + 1);
        }
        for (int i = 0; i < rows; i++) {
            System.arraycopy(squares[i], 0, copy.squares[i], 0, cols);
        }
        copy.completedSquaresCount = this.completedSquaresCount;
        return copy;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char getSquareOwner(int row, int col) {
        return squares[row][col];
    }
}