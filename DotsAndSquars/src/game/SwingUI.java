package game;

import javax.swing.*;
import java.awt.*;

public class SwingUI implements GameUI {
    private JFrame frame;
    private JTextArea logArea;
    private JPanel boardPanel;

    public SwingUI() {
        frame = new JFrame("Точки и квадраты");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Панель для лога
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Лог игры"));
        logScrollPane.setPreferredSize(new Dimension(300, 600));

        // Панель для игрового поля
        boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS)); // Вертикальное размещение компонентов
        boardPanel.setBorder(BorderFactory.createTitledBorder("Игровое поле"));
        boardPanel.setBackground(Color.WHITE);

        // Добавляем панели в основное окно
        frame.add(logScrollPane, BorderLayout.EAST);
        frame.add(boardPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    @Override
    public void displayMessage(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    @Override
    public void displayBoard(GameBoard board) {
        boardPanel.removeAll();

        Font font = new Font("Monospaced", Font.PLAIN, 14); // Моноширинный шрифт для точного выравнивания
        for (int i = 0; i <= board.getRows(); i++) {
            // Горизонтальные линии
            StringBuilder horizontalLine = new StringBuilder();
            for (int j = 0; j < board.getCols(); j++) {
                horizontalLine.append("+").append(board.getHorizontalEdges()[i][j] ? "---" : "   ");
            }
            horizontalLine.append("+");
            JLabel horizontalLabel = new JLabel(horizontalLine.toString(), SwingConstants.LEFT);
            horizontalLabel.setFont(font);
            boardPanel.add(horizontalLabel);

            // Вертикальные линии
            if (i < board.getRows()) {
                StringBuilder verticalLine = new StringBuilder();
                for (int j = 0; j <= board.getCols(); j++) {
                    verticalLine.append(board.getVerticalEdges()[i][j] ? "|" : " ")
                            .append(j < board.getCols() ? " " + (board.getSquareState(i, j).getOwner() != PlayerSymbol.NONE
                                    ? board.getSquareState(i, j).getOwner()
                                    : " ") + " " : "   ");
                }
                JLabel verticalLabel = new JLabel(verticalLine.toString(), SwingConstants.LEFT);
                verticalLabel.setFont(font);
                boardPanel.add(verticalLabel);
            }
        }

        // Обновляем интерфейс
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    @Override
    public String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(frame, prompt);
    }

    @Override
    public boolean askPlayAgain() {
        int result = JOptionPane.showConfirmDialog(frame, "Хотите сыграть ещё раз?", "Повторить?", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
