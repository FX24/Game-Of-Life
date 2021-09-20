package com.minibootcamp.gameoflife;

import java.util.Random;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.lang.Thread;


public class GameOfLife extends Canvas {

    // Board
    private static final int rowBoard = 100;
    private static final int colBoard = 100;
    private static final int[][] board = new int[rowBoard][colBoard];
    private static final int[][] nextBoard = new int[rowBoard][colBoard];

    // Constructor
    public GameOfLife() {
        Random rand = new Random();

        for(int row = 0; row < nextBoard.length; row++) {
            for(int col = 0; col < nextBoard[row].length; col++) {
                nextBoard[row][col] = rand.nextInt(2);
            }
        }
    }

    //Define the canvas (for Drawing)
    private static final int mPixel = 10;
    private static final int windowX = colBoard*mPixel;
    private static final int windowY = rowBoard*mPixel;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //Check every cell
        for(int row = 0; row < nextBoard.length; row++) {
            for(int col = 0; col < nextBoard[row].length; col++) {

                //Check if cell is Alive
                // 1 = Alive
                // 0 = Dead
                if(nextBoard[row][col] == 1) {
                    g.setColor(Color.BLUE);
                }
                else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(col*mPixel, row*mPixel, mPixel, mPixel);

                //Save the state of the GENERATION
                board[row][col] = nextBoard[row][col];
            }
        }

        //------------Game Of Life Algorithm---------///
        for(int row = 0; row < nextBoard.length; row++) {
            for(int col = 0; col < nextBoard[row].length; col++) {
                int aliveNeighbour = 0;

                // Check every neighbour.
                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        if(((row+i) < 0 || (row+i) >= nextBoard.length)
                                || ((col+j) < 0 || (col+j) >= nextBoard[row].length)
                                || (i == 0 && j == 0)) {
                            continue;
                        }

                        aliveNeighbour += board[row+i][col+j];
                    }
                }

                // Check for NEXT GENERATION
                if ((board[row][col]==1) && (aliveNeighbour<2)){
                    nextBoard[row][col] = 0;
                }
                else if ((board[row][col]==1) && (aliveNeighbour>3)) {
                    nextBoard[row][col] = 0;
                }
                else if ((board[row][col]==0) && (aliveNeighbour==3)){
                    nextBoard[row][col] = 1;
                }
                else {
                    nextBoard[row][col] = board [row][col];
                }
            }
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public static void main(String[] args) {

        GameOfLife task = new GameOfLife();

        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();

            frame.setSize(windowX, windowY);
            frame.setTitle("Game of Life (MINI BOOTCAMP)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(task);
            frame.setVisible(true);
        });
    }
}