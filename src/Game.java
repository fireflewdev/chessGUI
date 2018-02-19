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
    private boolean whiteTurn = true;
    private int firstI = -1,firstJ = -1,secondI = -1,secondJ = -1;
    private Piece[][] board;
    private JFrame frame = new JFrame();
    private final Color BG = Color.decode("#4D74B3");
    private final Color SELECT = Color.decode("#9F4DB3");
    private final Color PLACE = Color.decode("#62B34D");
    private final Color BLACK = Color.decode("#CCCCCC");
    private final Color WHITE = Color.decode("#222222");

    //constructor that manages the whole game
    public Game() {
        board = new Piece[8][8];

        //init game array
        //keep in mind that i is y and j is x. easy to get those confused
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                board[i][j] = new Piece('_',i,j); //_ = empty space
            }
        }
        repaint();

        //edit frame config
        frame.setSize(800, 800);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

        //add mouse listener
        addMouseListener(this);
    }

    //what to do on click
    public void mouseClicked(MouseEvent e) {
        int constraint = Math.min(frame.getWidth(),frame.getHeight()); //whichever side is the constraint
        int offset = constraint/10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        int side = constraint/10; //side of a tile
        int center = Math.max(frame.getWidth(),frame.getHeight());
        int centerOffset = (center - 8*side)/2;
        System.out.println("Mouse clicked!");
        if(firstI == -1){ //first click
            /*all calculations have to be in doubles or else the program will automatically round a small negative number to 0 since
             * it is dividing by the side length.
             */
            //setting firstI,J to corresponding pos on board.
            if(frame.getWidth() > frame.getHeight()){ //this is to check to see how the board is offset
                firstJ = (int)(Math.floor((double)(e.getX()-centerOffset)/(double)side)); //remember J = x, I = y!
                firstI = (int)(Math.floor((double)(e.getY()-offset)/(double)side));
            } else {
                firstJ = (int)(Math.floor((double)(e.getX()-offset)/(double)side)); //remember J = x, I = y!
                firstI = (int)(Math.floor((double)(e.getY()-centerOffset)/(double)side));
            }
            secondI = -1;
            secondJ = -1;
            System.out.println("First "+firstJ+","+firstI);
            if(!validFirst()){
                firstJ = -1; //since invalid
                firstI = -1;
                System.out.println("Invalid first");
                repaint();
                return;
            }
            repaint();
        } else {
            //setting secondI,J to corresponding pos on board.
            if(frame.getWidth() > frame.getHeight()){ //this is to check to see how the board is offset
                secondJ = (int)(Math.floor((double)(e.getX()-centerOffset)/(double)side)); //remember J = x, I = y!
                secondI = (int)(Math.floor((double)(e.getY()-offset)/(double)side));
            } else {
                secondJ = (int)(Math.floor((double)(e.getX()-offset)/(double)side)); //remember J = x, I = y!
                secondI = (int)(Math.floor((double)(e.getY()-centerOffset)/(double)side));
            }
            if(!validSecond()){
                secondJ = -1; //since it's invalid we are cancelling the move. whoops!
                secondI = -1;
                System.out.println("Invalid second");
                firstJ = -1; //setting all this to 0 so that we'll know we're on first click again!
                firstI = -1;
                repaint();
                return;
            }
            board[firstI][firstJ].setBoard(board); //give the selected piece a view of the board
            board[firstI][firstJ].move(secondI,secondJ); //move the piece on the SELECTED PIECE'S copy of the board
            board = board[firstI][firstJ].getBoard(); //set the main copy of the board to the selected piece's copy
            System.out.println("Second "+secondJ+","+secondI);
            firstJ = -1; //setting all this to 0 so that we'll know we're on first click again!
            firstI = -1;
            repaint();
        }
    }

    //renders board
    public void paint(Graphics g){
        int constraint = Math.min(frame.getWidth(),frame.getHeight()); //whichever side is the constraint
        int offset = constraint/10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        int side = constraint/10; //side of a tile
        int center = Math.max(frame.getWidth(),frame.getHeight());
        int centerOffset = (center - 8*side)/2;
        g.setColor(BG);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if((j+i)%2 == 1){
                    g.setColor(WHITE);
                    if(frame.getWidth() > frame.getHeight()) g.fillRect(j*side+centerOffset, i*side+offset, side, side);
                    else g.fillRect(j*side+offset, i*side+centerOffset, side, side);
                } else {
                    g.setColor(BLACK);
                    if(frame.getWidth() > frame.getHeight()) g.fillRect(j*side+centerOffset, i*side+offset, side, side);
                    else g.fillRect(j*side+offset, i*side+centerOffset, side, side);
                }
            }
        }
        if(firstI != -1){
            g.setColor(SELECT);
            g.fillRect(firstJ*side+offset, firstI*side+offset, side, side);
        }
    }
    public boolean validFirst(){
        if(firstI < 0 || firstI > 7){ //is it within board range?
            return false;
        }
        if(firstJ < 0 || firstJ > 7){ //is it within board range?
            return false;
        }
        if(board[firstI][firstJ].getType() == '_'){ //can't select an empty square!
            System.out.println("can't select an empty square, silly-head!");
            return false;
        }
        return true;
    }

    public boolean validSecond(){
        if(secondI < 0 || secondI > 7){ //is it within board range?
            return false;
        }
        if(secondJ < 0 || secondJ > 7){ //is it within board range?
            return false;
        }
        if(secondI == firstI && secondJ == firstJ){ //is the destination on the same tile as the starting point we selected?
            return false;
        }
        if(!board[firstI][firstJ].checkIfValidMove(secondI,secondJ)){ //check if it is a valid move according to specific piece's requirements.
            return false;
        }
        return true;
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

//please dont be retarded github, thank you!