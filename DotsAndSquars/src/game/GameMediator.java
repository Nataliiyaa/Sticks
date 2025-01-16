package game;

interface GameMediator {
    boolean makeMove(int row, int col, boolean isHorizontal, Player player);
}
