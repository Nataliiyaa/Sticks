package game;

public class ConsoleRenderer implements GameRenderer {
    @Override
    public void render(GameBoard board) {
        for (int i = 0; i <= board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                System.out.print("+" + (i <= board.getRows() && board.getHorizontalEdges()[i][j] ? "---" : "   "));
            }
            System.out.println("+");
            if (i < board.getRows()) {
                for (int j = 0; j <= board.getCols(); j++) {
                    System.out.print((j <= board.getCols() && board.getVerticalEdges()[i][j] ? "|" : " ") + " " + (j < board.getCols() && board.getSquareState(i, j).getOwner() != PlayerSymbol.NONE ? board.getSquareState(i, j).getOwner() : " ") + " ");
                }
                System.out.println();
            }
        }
    }
}
