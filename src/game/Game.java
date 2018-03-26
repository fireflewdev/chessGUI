package game;

import pieces.*;

import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private int constraint, offset, side, center, centerOffset; //offsets and stuff
    private int pI, pJ; //params for painting
    private BufferedImage paintImage;
    private Board board; //le board
    private JFrame frame = new JFrame(); //frame to put all the GUI on
    private File tempImg;
    private final Color BG = Color.decode("#31607d"); //color of background
    private final Color SELECT = Color.decode("#835996"); //color that selected square lights up
    private final Color WHITE = Color.decode("#97b0bf"); //color of blank white square
    private final Color BLACK = Color.decode("#597f96"); //color of blank black square
    private final Color VALIDWHITE = Color.decode("#bfaf97"); //color of valid white square
    private final Color VALIDBLACK = Color.decode("#967e59"); //color of valid black square
    private final Color TAKEWHITE = Color.decode("#97bfa7"); //color of a take-able white square
    private final Color TAKEBLACK = Color.decode("#599670"); //color of take-able black square
    private final String path = "res/";

    //constructor that manages the whole game
    public Game() {
        board = new Board();
        setUpBoard();
        System.out.println(toString());
        //edit frame config
        //frame.setSize(800, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        //frame.setBackground(BG);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        constraint = Math.min(frame.getWidth(), frame.getHeight()); //whichever side is the constraint
        offset = constraint / 10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        side = constraint / 10; //side of a tile
        center = Math.max(frame.getWidth(), frame.getHeight());
        centerOffset = (center - 8 * side) / 2;
        //load images:
        addMouseListener(this);
        redraw();
    }
    public void setUpBoard(){
        //init game array
        //keep in mind that i is y and j is x. easy to get those confused
        int j = 0;
        new Rook(PieceColor.BLACK, 0, j++, board);
        new Knight(PieceColor.BLACK, 0, j++, board);
        new Bishop(PieceColor.BLACK, 0, j++, board);
        new Queen(PieceColor.BLACK, 0, j++, board);
        new King(PieceColor.BLACK, 0, j++, board);
        new Bishop(PieceColor.BLACK, 0, j++, board);
        new Knight(PieceColor.BLACK, 0, j++, board);
        new Rook(PieceColor.BLACK, 0, j++, board);

        for (j = 0; j < board.getSize(); j++) {
            new Pawn(PieceColor.BLACK, 1, j, board); //_ = empty space
        }
        for (int i = 2; i < board.getSize() - 2; i++) {
            for (j = 0; j < board.getSize(); j++) {
                new Blank(PieceColor.BLANK, i, j, board); //_ = empty space
            }
        }
        for (j = 0; j < board.getSize(); j++) {
            new Pawn(PieceColor.WHITE, 6, j, board); //_ = empty space
        }
        j = 0;
        new Rook(PieceColor.WHITE, 7, j++, board);
        new Knight(PieceColor.WHITE, 7, j++, board);
        new Bishop(PieceColor.WHITE, 7, j++, board);
        new Queen(PieceColor.WHITE, 7, j++, board);
        new King(PieceColor.WHITE, 7, j++, board);
        new Bishop(PieceColor.WHITE, 7, j++, board);
        new Knight(PieceColor.WHITE, 7, j++, board);
        new Rook(PieceColor.WHITE, 7, j++, board);

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
                int tempI = firstI;
                int tempJ = firstJ;
                firstJ = -1; //since invalid
                firstI = -1;
                System.out.println("Invalid first");
                return;
            }
            redraw();
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
                int tempI = firstI;
                int tempJ = firstJ;
                secondJ = -1; //since it's invalid we are cancelling the move. whoops!
                secondI = -1;
                System.out.println("Invalid second");
                firstJ = -1; //setting all this to 0 so that we'll know we're on first click again!
                firstI = -1;
                redraw();
                return;
            }

            /*MOVING THE PIECES*/

            board.getPiece(firstI, firstJ).move(secondI, secondJ);
            System.out.println("Second " + secondJ + "," + secondI);
            System.out.println(this.toString());
            firstJ = -1; //setting all this to 0 so that we'll know we're on first click again!
            firstI = -1;
            whiteTurn = !whiteTurn; //switches turn
            redraw();
        }
    }

    public boolean validFirst() {
        if (firstI < 0 || firstI > 7) { //is it within board range?
            return false;
        }
        if (firstJ < 0 || firstJ > 7) { //is it within board range?
            return false;
        }
        if (board.isEmpty(firstI, firstJ)) { //can't select an empty square!
            System.out.println("can't select an empty square, silly-head!");
            return false;
        }
        if (board.getPiece(firstI, firstJ).isWhite() && !whiteTurn || board.getPiece(firstI, firstJ).isBlack() && whiteTurn) { //can't select opposite color piece!
            System.out.println("can't select an opposite color piece, silly-head!");
            return false;
        }
        return true;
    }

    public boolean validSecond() {
        if (secondI < 0 || secondI > 7) { //is it within board range?
            return false;
        } else if (secondJ < 0 || secondJ > 7) { //is it within board range?
            return false;
        } else if (secondI == firstI && secondJ == firstJ) { //is the destination on the same tile as the starting point we selected?
            return false;
        } else if (!board.getPiece(firstI, firstJ).canMove(secondI, secondJ)) { //check if it is a valid move according to specific piece's requirements.
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
        //draw board
        g.setColor(BG);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //draw pattern
                if(firstI == -1 || !board.getPiece(firstI,firstJ).canMove(i,j)) {
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
                } else {
                    if ((j + i) % 2 == 1) {
                        g.setColor(!board.isEmpty(i,j)?TAKEWHITE:VALIDWHITE); //set color to TAKEWHITE if takeable and VALIDWHITE if valid
                        if (frame.getWidth() > frame.getHeight())
                            g.fillRect(j * side + centerOffset, i * side + offset, side, side);
                        else g.fillRect(j * side + offset, i * side + centerOffset, side, side);

                    } else {
                        g.setColor(!board.isEmpty(i,j)?TAKEBLACK:VALIDBLACK);
                        if (frame.getWidth() > frame.getHeight())
                            g.fillRect(j * side + centerOffset, i * side + offset, side, side);
                        else g.fillRect(j * side + offset, i * side + centerOffset, side, side);
                    }
                }
                if (firstI != -1) {
                    if (firstI == i && firstJ == j) {
                        g.setColor(SELECT);
                        if (frame.getWidth() > frame.getHeight())
                            g.fillRect(firstJ * side + centerOffset, firstI * side + offset, side, side);
                        else g.fillRect(firstJ * side + offset, firstI * side + centerOffset, side, side);
                    }
                }
                //draw piece
                board.getPiece(i, j).paintPiece(frame, g);
            }
        }
    }

    public void redraw() {
        repaint();
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

    public String toString() {
        return board.toString() +"\n" +(whiteTurn?"White's turn":"Black's turn");
    }
}