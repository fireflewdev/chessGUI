import javax.swing.*;
import java.awt.*;

public class Game extends JPanel{
    private int[][] board;

    /*
     * What should be here:
     * -8x8 array with board pieces
     * -GUI
     * -An interface to interact with the board
     */
    public Game() {
        board = new int[8][8];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = 0;//0 = empty
            }
        }

    }


    public void paint(Graphics g){
        g.fillRect(100, 100, 400, 400);
        for(int i = 100; i <= 400; i+=100){
            for(int j = 100; j <= 400; j+=100){
                g.clearRect(i, j, 50, 50);
            }
        }

        for(int i = 150; i <= 450; i+=100){
            for(int j = 150; j <= 450; j+=100){
                g.clearRect(i, j, 50, 50);
            }
        }
    }
}

