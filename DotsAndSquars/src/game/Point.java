package game;

public class Point {
    private final int row;
    private final int col;
    boolean isHorizontal;

    public Point(int row, int col, boolean isHorizontal) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }
}
