package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWindow extends JFrame {
    private GameBoard board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public GameWindow() {
        setTitle("Точки и квадраты");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void start() {
        int rows, cols;
        while (true) {
            String sizeInput = JOptionPane.showInputDialog(this, "Введите количество строк и столбцов игрового поля через пробел (минимум 2x2): ");
            String[] dimensions = sizeInput.split(" ");
            try {
                rows = Integer.parseInt(dimensions[0]);
                cols = Integer.parseInt(dimensions[1]);
            } catch (Exception e) {
                System.out.println("Ошибка ввода. Убедитесь, что вы ввели два числа через пробел.");
                continue;
            }
            if (rows >= 2 && cols >= 2) {
                break;
            } else {
                System.out.println("Размеры игрового поля должны быть не менее 2x2. Попробуйте снова.");
            }
        }

        board = new GameBoard(rows, cols);
        player1 = new HumanPlayer(PlayerSymbol.X, null); // Управление мышью
        player2 = new BotPlayer(PlayerSymbol.O);
        currentPlayer = player1;

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        add(panel);
        setVisible(true);
    }

    private void drawBoard(Graphics g) {
        int rows = board.getRows();
        int cols = board.getCols();
        int cellSize = Math.min(getWidth(), getHeight()) / Math.max(rows, cols);

        // Рисуем точки и линии
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                g.fillOval(j * cellSize - 3, i * cellSize - 3, 6, 6);
            }
        }

        // Рисуем завершённые квадраты
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                char owner = board.getSquareOwner(i, j);
//                if (owner != '\u0000') {
//                    g.drawString(String.valueOf(owner), j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
//                }
//            }
//        }
    }

    private void handleMouseClick(int x, int y) {
        // Обработчик нажатий мыши для выполнения хода
        System.out.println("Клик по координатам: " + x + ", " + y);
    }
}
