import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JPanel implements MouseListener{
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
        for(int i = 100; i <= 500; i += 100){
            for(int j = 100; j <= 500; j += 100){
                g.clearRect(i, j, 50, 50);
            }
        }

        for(int i = 150; i <= 550; i += 100){
            for(int j = 150; j <= 550; j += 100){
                g.clearRect(i, j, 50, 50);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

