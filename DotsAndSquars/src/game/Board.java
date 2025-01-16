package game;

public interface Board {
    int getRows();
    int getCols();
    boolean isFull();
    char getSquareOwner(int row, int col);
}
