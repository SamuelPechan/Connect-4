import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ConnectFour {
    private String message = "";
    // frame size
    private int gridWidth = 700;
    private int gridHeight = 600;
    // game board size
    private int row = 6;
    private int column = 7;
    private int[][] gameBoard = new int[row][column];
    // current column and player
    private int currCol = 0;
    private int currPlayer = 1;
    // make window
    JFrame frame = new JFrame("Connect Four");
    JPanel panel = new JPanel() {
        ;
        @Override
        public void paintComponent(Graphics g) {
            setPreferredSize(new Dimension(gridWidth, gridHeight));
            super.paintComponent(g);
            // draw game grid
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < column; c++) {
                    int x = c * 100;
                    int y = r * 100;
                    g.drawRect(x + 40, y + 10, 100, 100);
                    if (c == currCol) {
                        g.setColor(Color.CYAN);
                        g.fillRect(x + 40, y + 10, 100, 100);
                    }
                    // will be two player game, set color to red when p1 goes and yellow when p2
                    // goes
                    if (gameBoard[r][c] == 1) {
                        g.setColor(Color.RED);
                        g.fillOval(x + 50, y + 20, 80, 80);
                    } else if (gameBoard[r][c] == 2) {
                        g.setColor(Color.YELLOW);
                        g.fillOval(x + 50, y + 20, 80, 80);
                    }
                    g.setColor(Color.BLUE);
                    g.drawRect(x + 40, y + 10, 100, 100);
                }
            }
            if (!message.isEmpty()) {
                g.setFont(new Font("Arial", Font.PLAIN, 50));
                g.setColor(Color.BLACK);
                g.drawString(message, 250, 300);
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton moveLeft = new JButton("move left");
    JButton moveRight = new JButton("move right");
    JButton drop = new JButton("drop piece");
    JButton reset = new JButton("reset");

    // check win conditions
    public boolean checkWin() {
        // horizontal 4 in a row
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column - 3; c++) {
                if (gameBoard[r][c] != 0 &&
                        gameBoard[r][c] == gameBoard[r][c + 1] &&
                        gameBoard[r][c] == gameBoard[r][c + 2] &&
                        gameBoard[r][c] == gameBoard[r][c + 3]) {
                    return true;
                }
            }
        }
        // vertical win
        for (int c = 0; c < column; c++) {
            for (int r = 0; r < row - 3; r++) {
                if (gameBoard[r][c] != 0 &&
                        gameBoard[r][c] == gameBoard[r + 1][c] &&
                        gameBoard[r][c] == gameBoard[r + 1][c] &&
                        gameBoard[r][c] == gameBoard[r + 1][c]) {
                    return true;
                }
            }
        }
        // diagonal bottom left to top right win
        for (int r = 0; r < row - 3; r++) {
            for (int c = 0; c < column - 3; c++) {
                if (gameBoard[r][c] != 0 &&
                        gameBoard[r][c] == gameBoard[r + 1][c + 1] &&
                        gameBoard[r][c] == gameBoard[r + 2][c + 2] &&
                        gameBoard[r][c] == gameBoard[r + 3][c + 3]) {
                    return true;
                }
            }
        }
        // diagonal bottom right to top left win
        for (int r = 0; r > row + 3; r++) {
            for (int c = 0; c > column + 3; c++) {
                if (gameBoard[r][c] != 0 &&
                        gameBoard[r][c] == gameBoard[r + 1][c - 1] &&
                        gameBoard[r][c] == gameBoard[r + 2][c - 2] &&
                        gameBoard[r][c] == gameBoard[r + 3][c - 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetGame() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < column; c++) {
                gameBoard[r][c] = 0;
            }
        }
        moveLeft.setEnabled(true);
        moveRight.setEnabled(true);
        drop.setEnabled(true);
        currPlayer = 1;
        message = "";
        panel.repaint();
    }

    ConnectFour() {
        // frame
        frame.setVisible(true);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // panel
        frame.add(panel, BorderLayout.CENTER);
        // button Panel
        frame.add(buttonPanel, BorderLayout.SOUTH);
        // buttons
        moveLeft.setFocusable(false);
        buttonPanel.add(moveLeft);
        drop.setFocusable(false);
        buttonPanel.add(drop);
        moveRight.setFocusable(false);
        buttonPanel.add(moveRight);
        reset.setFocusable(false);
        buttonPanel.add(reset);
        // button actions
        moveLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currCol > 0) {
                    currCol--;
                    panel.repaint();
                }
            }
        });
        moveRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currCol < column - 1) {
                    currCol++;
                    panel.repaint();
                }
            }
        });
        drop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int r = row - 1; r >= 0; r--) {
                    if (gameBoard[r][currCol] == 0) {// if empty
                        gameBoard[r][currCol] = currPlayer;// put current players piece in the spot
                        if (checkWin() == true) {
                            message = "player " + currPlayer + " wins!";
                            moveLeft.setEnabled(false);
                            moveRight.setEnabled(false);
                            drop.setEnabled(false);
                            panel.repaint();
                            return;
                        } else {
                            currPlayer = (currPlayer == 1) ? 2 : 1;
                            panel.repaint();
                        }
                        break;
                    }
                }
            }
        });
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
    }
}