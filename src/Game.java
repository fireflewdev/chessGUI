import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * What should be here:
 * -8x8 array with board pieces
 * -GUI
 * -An interface to interact with the board
 */
public class Game extends JPanel implements MouseListener{
    private int[][] board;
    private JFrame frame = new JFrame();
    private final Color BG = Color.decode("#4D74B3");
    private final Color BLACK = Color.decode("#CCCCCC");
    private final Color WHITE = Color.decode("#111111");
    public Game() {
        board = new int[8][8];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = 0;//0 = empty
            }
        }
        repaint();
        frame.setSize(800, 800);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void paint(Graphics g){
        g.setColor(BG);
        g.fillRect(100, 100, 400, 400);
        int constraint = Math.min(frame.getWidth(),frame.getHeight());
        int offset = constraint/10;
        int side = constraint/10;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((j+i)%2 == 1){
                    g.setColor(WHITE);
                    g.fillRect(j*side+offset, i*side+offset, side, side);
                } else {
                    g.setColor(BLACK);
                    g.fillRect(j*side+offset, i*side+offset, side, side);
                }
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

//please dont be retarded github