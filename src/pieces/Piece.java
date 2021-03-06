package pieces;

import game.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/*
  * different types of pieces
  * based on the param the piece will act a certain way. using char so its easier to manage
  * _ = empty piece
  * 1 king= pieces.Piece(k)
  * 1 queen = pieces.Piece(q)
  * 2 rooks = pieces.Piece(r)
  * 2 bishops = pieces.Piece(b)
  * 2 knights = pieces.Piece(h), changing to h (horse) so its easier to differentiate King from pieces.Knight as K and k look similar
  * 8 pawns = pieces.Piece(p)
  */
public abstract class Piece {
    protected Board board;
    protected boolean moved = false;
    protected int currentI, currentJ; //current i an j values on board when piece invoked. remember i = y and j = x!
    protected int factor;
    protected PieceColor color; //colors: w, b, _. "_" is color for blank squares.
    protected char taken; //which type taken
    protected Image image;

    public Piece(PieceColor color, int currentI, int currentJ, Board board){
        this.board = board;
        this.color = color;
        this.currentI = currentI;
        this.currentJ = currentJ;
        taken = '_'; //nothing taken
        factor = this.color == PieceColor.WHITE?-1:1; //blacks checking will be opposite of white so we make it negative
        board.putPiece(currentI, currentJ, this);

    }

    //removed board editing stuff since that is now all done in the game.Game class.

    public PieceColor getColor(){
        return color;
    }
    
    protected boolean checkIfEmptyInALine(int fi, int fj, int li, int lj){ //for everyone else
        int f = Math.min(fi, li);
        int l = Math.max(fi, li);
        for (int i = f; i <= l; i++) {
            if(i != fi &&  i != li) {
                if (!board.isEmpty(i, fj)) {
                    return false;
                }
            }
        }
        f = Math.min(fj, lj);
        l = Math.max(fj, lj);
        for (int j = f; j <= l; j++) {
            if(j != fj &&  j != lj) {
                if (!board.isEmpty(fi, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkIfEmptyInADiagonal(int fi, int fj, int li, int lj) {
        int steps = Math.abs(li-fi);
        int signi = (int) Math.signum(li - fi);
        int signj = (int) Math.signum(lj - fj);
        if(steps != Math.abs(fj-lj)) {
            return false;
        }
        for (int step = 1; step < steps; step++) {
            if (!board.isEmpty(fi + signi*step, fj + signj*step)) {
                return false;
            }
        }
        return true;
    }

    public void takePiece(int i, int j){
        //taken = board[i][j].getType();
    }
    public boolean move(int i, int j){
        if(canMove(i, j)) {
            moved = true;
            board.putPiece(currentI,currentJ,new Blank(PieceColor.BLANK, currentI, currentJ, board));
            currentI = i;
            currentJ = j;
            board.putPiece(i,j,this);
            return true;
        } else {
            System.out.println("invalid "+getClass().toString()+" move at steps: " + (i) + " , " + (j));
            return false;
        }
    }

    //renders piece
    public void paintPiece(JFrame frame, Graphics g){
        int constraint = Math.min(frame.getWidth(), frame.getHeight()); //whichever side is the constraint
        int offset = constraint / 10; // divided by 10 because there are 10 slots: offset, 8 tiles, another offset
        int side = constraint / 10; //side of a tile
        int center = Math.max(frame.getWidth(), frame.getHeight());
        int centerOffset = (center - 8 * side) / 2;
        if (frame.getWidth() > frame.getHeight()) //constraint image
            g.drawImage(image, currentJ * side + centerOffset, currentI * side + offset, side, side,null);
        else g.drawImage(image, currentJ * side + offset, currentI * side + centerOffset, side, side, null);
    }

    public boolean isWhite(){
        return color == PieceColor.WHITE;
    }

    public boolean isBlack(){
        return color == PieceColor.BLACK;
    }

    protected void loadImage(String name){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(name + ".png");
        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("cannot load "+name);
            e.printStackTrace();
        }
    }
    //given a certain i and j to move (remember i = y and j = x) check to see if valid move.
    public boolean canMove(int i, int j){
        if(color == board.getPiece(i,j).getColor()) {
            return false; //can't interact with same color pieces!
        }
        return isValidMove(i, j);
    }

    protected abstract boolean isValidMove(int i, int j);

    public abstract char toChar();

}
