package game;

public class GameBoard implements Board{
    private final int rows;
    private final int cols;
    private final boolean[][] horizontalEdges;
    private final boolean[][] verticalEdges;
    private final SquareState[][] squares;
    private int completedSquaresCount;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.horizontalEdges = new boolean[rows + 1][cols];
        this.verticalEdges = new boolean[rows][cols + 1];
        this.squares = new SquareState[rows][cols];
        this.completedSquaresCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                squares[i][j] = new SquareState();
            }
        }
    }

    public boolean[][] getHorizontalEdges() {
        return horizontalEdges;
    }

    public boolean[][] getVerticalEdges() {
        return verticalEdges;
    }

    public SquareState getSquareState(int row, int col) {
        return squares[row][col];
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

    public int checkAndMarkSquares(int row, int col, boolean isHorizontal, SquareState playerSymbol) {
        int completedSquares = 0;

        if (isHorizontal) {
            if (row < rows && col < cols && isSquareComplete(row, col)) {
                squares[row][col].setOwner(playerSymbol.getOwner());
                completedSquares++;
            }
            if (row > 0 && col < cols && isSquareComplete(row - 1, col)) {
                squares[row - 1][col].setOwner(playerSymbol.getOwner());
                completedSquares++;
            }
        } else {
            if (row < rows && col < cols && isSquareComplete(row, col)) {
                squares[row][col].setOwner(playerSymbol.getOwner());
                completedSquares++;
            }
            if (col > 0 && row < rows && isSquareComplete(row, col - 1)) {
                squares[row][col - 1].setOwner(playerSymbol.getOwner());
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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}

