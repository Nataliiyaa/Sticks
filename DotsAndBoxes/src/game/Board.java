package game;

public interface Board {
    int getRows();
    int getCols();
    boolean isFull();
    SquareState getSquareState(int row, int col);
}
