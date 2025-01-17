package game;

import java.util.Scanner;

public class ConsoleUI implements GameUI {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayBoard(GameBoard board) {
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

    @Override
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public boolean askPlayAgain() {
        String response = getUserInput("Хотите сыграть ещё раз? (да/нет): ").trim().toLowerCase();
        return response.equals("да") || response.equals("yes");
    }
}
