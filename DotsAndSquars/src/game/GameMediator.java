package game;

import java.util.List;

interface GameMediator {
    boolean makeMove(int row, int col, boolean isHorizontal, Player player);
    int getBoardRows();
    int getBoardCols();
    void calculateAndDisplayScores(Player player1, Player player2);
}
