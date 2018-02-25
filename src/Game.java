import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import javax.swing.*;

/*
 * What should be here:
 * -8x8 array with board pieces
 * -GUI
 * -An interface to interact with the board
 */
public class Game extends JPanel implements MouseListener {
    private Image image;
    private boolean whiteTurn = true; //is it white's turn? initialized as true since white goes first
    private int firstI = -1, firstJ = -1, secondI = -1, secondJ = -1; //firstI,J: selected square. secondI,J: destination square.
    private Piece[][] board; //le board
    private JFrame frame = new JFrame(); //frame to put all the GUI on
    private final Color BG = Color.decode("#31607d"); //color of background
    private final Color SELECT = Color.decode("#835996"); //color that selected square lights up
    private final Color BLACK = Color.decode("#597f96"); //color of black square
    private final Color WHITE = Color.decode("#97b0bf"); //color of white square
    private final int imageScale = Image.SCALE_DEFAULT;
    private int constraint, offset, side, center, centerOffset;
    public Image wking, bking, wknight, bknight, wbishop, bbishop, wqueen, bqueen, wrook, brook, wpawn, bpawn;

    //constructor that manages the whole game
    public Game() {
        board = new Piece[8][8];
        //init game array
        //keep in mind that i is y and j is x. easy to get those confused
        char[] pieces = {'r', 'h', 'b', 'k', 'q', 'b', 'h', 'r'}; //array of chars specifying board setup
        for (int j = 0; j < board.length; j++) {
            board[0][j] = new Piece(pieces[pieces.length-j-1], 'b', 0, j); //_ = empty space
        }
        for (int j = 0; j < board.length; j++) {
            board[1][j] = new Piece('p', 'b', 1, j); //_ = empty space
        }
        for (int i = 2; i < board.length-2; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Piece('_', '_', i, j); //_ = empty space
            }
        }
        for (int j = 0; j < board.length; j++) {
            board[6][j] = new Piece('p', 'w', 6, j); //_ = empty space
        }
        for (int j = 0; j < board.length; j++) {
            board[7][j] = new Piece(pieces[j], 'w', 7, j); //_ = empty space
        }
        System.out.println(this.toString());
        //edit frame config
        frame.setSize(800, 800);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setBackground(BG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        constraint = Math.min(frame.getWidth(), frame.getHeight()); //whichever side is the constraint
        offset = constraint / 10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        side = constraint / 10; //side of a tile
        center = Math.max(frame.getWidth(), frame.getHeight());
        centerOffset = (center - 8 * side) / 2;
        //load images:
        try {
            wking = ImageIO.read(new File("images/whiteking.png"));
            bking = ImageIO.read(new File("images/blackking.png"));
            wknight = ImageIO.read(new File("images/whiteknight.png"));
            bknight = ImageIO.read(new File("images/blackknight.png"));
            wbishop = ImageIO.read(new File("images/whitebishop.png"));
            bbishop = ImageIO.read(new File("images/blackbishop.png"));
            wqueen = ImageIO.read(new File("images/whitequeen.png"));
            bqueen = ImageIO.read(new File("images/blackqueen.png"));
            wrook = ImageIO.read(new File("images/whiterook.png"));
            brook = ImageIO.read(new File("images/blackrook.png"));
            wpawn = ImageIO.read(new File("images/whitepawn.png"));
            bpawn = ImageIO.read(new File("images/blackpawn.png"));
        } catch (IOException e) {
            System.out.print("ERROR: a file is not found");
        }
        addMouseListener(this);
        repaint();
    }

    //what to do on click
    public void mouseClicked(MouseEvent e) {
        constraint = Math.min(frame.getWidth(), frame.getHeight()); //whichever side is the constraint
        offset = constraint / 10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        side = constraint / 10; //side of a tile
        center = Math.max(frame.getWidth(), frame.getHeight());
        centerOffset = (center - 8 * side) / 2;
        System.out.println("Mouse clicked!");
        if (firstI == -1) { //first click
            /*all calculations have to be in doubles or else the program will automatically round a small negative number to 0 since
             * it is dividing by the side length.
             */
            //setting firstI,J to corresponding pos on board.
            if (frame.getWidth() > frame.getHeight()) { //this is to check to see how the board is offset
                firstJ = (int) (Math.floor((double) (e.getX() - centerOffset) / (double) side)); //remember J = x, I = y!
                firstI = (int) (Math.floor((double) (e.getY() - offset) / (double) side));
            } else {
                firstJ = (int) (Math.floor((double) (e.getX() - offset) / (double) side)); //remember J = x, I = y!
                firstI = (int) (Math.floor((double) (e.getY() - centerOffset) / (double) side));
            }
            secondI = -1;
            secondJ = -1;
            System.out.println("First " + firstJ + "," + firstI);
            if (!validFirst()) {
                firstJ = -1; //since invalid
                firstI = -1;
                System.out.println("Invalid first");
                repaint();
                return;
            }
            repaint();
        } else {
            //setting secondI,J to corresponding pos on board.
            if (frame.getWidth() > frame.getHeight()) { //this is to check to see how the board is offset
                secondJ = (int) (Math.floor((double) (e.getX() - centerOffset) / (double) side)); //remember J = x, I = y!
                secondI = (int) (Math.floor((double) (e.getY() - offset) / (double) side));
            } else {
                secondJ = (int) (Math.floor((double) (e.getX() - offset) / (double) side)); //remember J = x, I = y!
                secondI = (int) (Math.floor((double) (e.getY() - centerOffset) / (double) side));
            }
            if (!validSecond()) {
                secondJ = -1; //since it's invalid we are cancelling the move. whoops!
                secondI = -1;
                System.out.println("Invalid second");
                firstJ = -1; //setting all this to 0 so that we'll know we're on first click again!
                firstI = -1;
                repaint();
                return;
            }
            Piece temp = board[firstI][firstJ];
            board[firstI][firstJ] =  board[secondI][secondJ];
            board[secondI][secondJ] = temp;
            //board = board[firstI][firstJ].getBoard();
            System.out.println("Second " + secondJ + "," + secondI);
            System.out.println(this.toString());
            firstJ = -1; //setting all this to 0 so that we'll know we're on first click again!
            firstI = -1;
            whiteTurn = !whiteTurn; //switches turn
            repaint();
        }
    }

    public boolean validFirst() {
        if (firstI < 0 || firstI > 7) { //is it within board range?
            return false;
        }
        if (firstJ < 0 || firstJ > 7) { //is it within board range?
            return false;
        }
        if (board[firstI][firstJ].getType() == '_') { //can't select an empty square!
            System.out.println("can't select an empty square, silly-head!");
            return false;
        }
        if (board[firstI][firstJ].getColor() == 'w' && !whiteTurn || board[firstI][firstJ].getColor() == 'b' && whiteTurn) { //can't select opposite color piece!
            System.out.println("can't select an opposite color piece, silly-head!");
            return false;
        }
        return true;
    }

    public boolean validSecond() {
        if (secondI < 0 || secondI > 7) { //is it within board range?
            return false;
        }
        if (secondJ < 0 || secondJ > 7) { //is it within board range?
            return false;
        }
        if (secondI == firstI && secondJ == firstJ) { //is the destination on the same tile as the starting point we selected?
            return false;
        }
        if (!board[firstI][firstJ].checkIfValidMove(secondI, secondJ, board)) { //check if it is a valid move according to specific piece's requirements.
            return false;
        }
        return true;
    }

    //renders board
    public void paint(Graphics g) {
        constraint = Math.min(frame.getWidth(), frame.getHeight()); //whichever side is the constraint
        offset = constraint / 10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        side = constraint / 10; //side of a tile
        center = Math.max(frame.getWidth(), frame.getHeight());
        centerOffset = (center - 8 * side) / 2;
        g.setColor(BG);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((j + i) % 2 == 1) {
                    g.setColor(WHITE);
                    if (frame.getWidth() > frame.getHeight())
                        g.fillRect(j * side + centerOffset, i * side + offset, side, side);
                    else g.fillRect(j * side + offset, i * side + centerOffset, side, side);

                } else {
                    g.setColor(BLACK);
                    if (frame.getWidth() > frame.getHeight())
                        g.fillRect(j * side + centerOffset, i * side + offset, side, side);
                    else g.fillRect(j * side + offset, i * side + centerOffset, side, side);
                }
                if (firstI != -1) {
                    if(firstI == i && firstJ == j) {
                        g.setColor(SELECT);
                        if (frame.getWidth() > frame.getHeight())
                            g.fillRect(firstJ * side + centerOffset, firstI * side + offset, side, side);
                        else g.fillRect(firstJ * side + offset, firstI * side + centerOffset, side, side);
                    }
                }
                char type = board[i][j].getType();
                if(type == 'k') {
                    if (board[i][j].getColor() == 'w') {
                        image = wking.getScaledInstance(side, side, imageScale); //scale image
                        if (frame.getWidth() > frame.getHeight()) //constraint image
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    } else {
                        image = bking.getScaledInstance(side, side, imageScale);
                        if (frame.getWidth() > frame.getHeight())
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    }
                }
                else if(type == 'h') {
                    if (board[i][j].getColor() == 'w') {
                        image = wknight.getScaledInstance(side, side, imageScale); //scale image
                        if (frame.getWidth() > frame.getHeight()) //constraint image
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    } else {
                        image = bknight.getScaledInstance(side, side, imageScale);
                        if (frame.getWidth() > frame.getHeight())
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    }
                }
                else if(type == 'b') {
                    if (board[i][j].getColor() == 'w') {
                        image = wbishop.getScaledInstance(side, side, imageScale); //scale image
                        if (frame.getWidth() > frame.getHeight()) //constraint image
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    } else {
                        image = bbishop.getScaledInstance(side, side, imageScale);
                        if (frame.getWidth() > frame.getHeight())
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    }
                }
                else if(type == 'q') {
                    if (board[i][j].getColor() == 'w') {
                        image = wqueen.getScaledInstance(side, side, imageScale); //scale image
                        if (frame.getWidth() > frame.getHeight()) //constraint image
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    } else {
                        image = bqueen.getScaledInstance(side, side, imageScale);
                        if (frame.getWidth() > frame.getHeight())
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    }
                }
                else if(type == 'r') {
                    if (board[i][j].getColor() == 'w') {
                        image = wrook.getScaledInstance(side, side, imageScale); //scale image
                        if (frame.getWidth() > frame.getHeight()) //constraint image
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    } else {
                        image = brook.getScaledInstance(side, side, imageScale);
                        if (frame.getWidth() > frame.getHeight())
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    }
                }
                else if(type == 'p') {
                    if (board[i][j].getColor() == 'w') {
                        image = wpawn.getScaledInstance(side, side, imageScale); //scale image
                        if (frame.getWidth() > frame.getHeight()) //constraint image
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    } else {
                        image = bpawn.getScaledInstance(side, side, imageScale);
                        if (frame.getWidth() > frame.getHeight())
                            g.drawImage(image, j * side + centerOffset, i * side + offset, null);
                        else g.drawImage(image, j * side + offset, i * side + centerOffset, null);
                    }
                }
            }
        }

    }

    public Dimension getPreferredSize() {
        if (wking == null) {
            return new Dimension(constraint, constraint);
        } else {
            return new Dimension(constraint, constraint);
        }
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
    public String toString(){
        String out = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if(board[i][j].getColor() != 'b') out+=board[i][j].getType() + " ";
                else out+=(char)(board[i][j].getType()-32) + " ";

            }
            out+="\n";
        }
        return out;
    }
}