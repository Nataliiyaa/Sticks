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
        String sizeInput = JOptionPane.showInputDialog(this, "Введите размер игрового поля:");
        int size = Integer.parseInt(sizeInput);

        board = new GameBoard(size);
        player1 = new HumanPlayer('X', null); // Управление мышью
        player2 = new BotPlayer('O');
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
        int size = board.getSize();
        int cellSize = Math.min(getWidth(), getHeight()) / size;

        // Рисуем точки и линии
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                g.fillOval(j * cellSize - 3, i * cellSize - 3, 6, 6);
            }
        }

        // Рисуем завершённые квадраты
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char owner = board.getSquareOwner(i, j);
                if (owner != '\u0000') {
                    g.drawString(String.valueOf(owner), j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
                }
            }
        }
    }

    private void handleMouseClick(int x, int y) {
        // Обработчик нажатий мыши для выполнения хода
        System.out.println("Клик по координатам: " + x + ", " + y);
    }
}
