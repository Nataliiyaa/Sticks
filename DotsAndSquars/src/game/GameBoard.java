package game;

public class GameBoard {
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

    public GameBoard copy() {
        GameBoard copy = new GameBoard(size);
        for (int i = 0; i <= size; i++) {
            System.arraycopy(horizontalEdges[i], 0, copy.horizontalEdges[i], 0, size);
        }
        for (int i = 0; i < size; i++) {
            System.arraycopy(verticalEdges[i], 0, copy.verticalEdges[i], 0, size + 1);
        }
        for (int i = 0; i < size; i++) {
            System.arraycopy(squares[i], 0, copy.squares[i], 0, size);
        }
        return copy;
    }

    public int getSize() {
        return size;
    }

    public char getSquareOwner(int row, int col) {
        return squares[row][col];
    }
}