package game;

import pieces.*;

public class Board {
    private Piece[][] board;

    public Board(){
        board = new Piece[8][8];
        //init game array
        //keep in mind that i is y and j is x. easy to get those confused
        int j  = 0;
        board[0][j] = new Rook(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new Knight(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new Bishop(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new Queen(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new King(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new Bishop(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new Knight(PieceColor.BLACK, 0,j++, this);
        board[0][j] = new Rook(PieceColor.BLACK, 0,j++, this);

        for (j = 0; j < board.length; j++) {
            board[1][j] = new Pawn(PieceColor.BLACK, 1,j, this); //_ = empty space
        }
        for (int i = 2; i < board.length - 2; i++) {
            for (j = 0; j < board.length; j++) {
                board[i][j] = new Blank(PieceColor.BLANK, i, j, this); //_ = empty space
            }
        }
        for (j = 0; j < board.length; j++) {
            board[6][j] = new Pawn(PieceColor.WHITE, 6,j, this); //_ = empty space
        }
        j = 0;
        board[7][j] = new Rook(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new Knight(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new Bishop(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new Queen(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new King(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new Bishop(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new Knight(PieceColor.WHITE, 7,j++, this);
        board[7][j] = new Rook(PieceColor.WHITE, 7,j++, this);

    }

    public boolean isEmpty(int i, int j){
        return board[i][j] instanceof Blank;
    }
    public Piece getPiece(int i, int j){
        return board[i][j];
    }
    public void putPiece(int i, int j, Piece piece){
        board[i][j] = piece;
    }
    /*public String toString(){
        String out = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].getColor() != 'b') out += board[i][j].getType() + " ";
                else out += (char) (board[i][j].getType() - 32) + " ";
            }
            out += "\n";
        }
        return out;
    }*/
}
