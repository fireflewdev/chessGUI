package game;

import pieces.*;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    public boolean isEmpty(int i, int j) {
        return board[i][j] instanceof Blank;
    }

    public Piece getPiece(int i, int j) {
        return board[i][j];
    }

    public int getSize(){
        return board.length;
    }

    public void putPiece(int i, int j, Piece piece) {
        board[i][j] = piece;
    }

    public String toString() {
        String out = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                out += getPiece(i, j).toChar() + " ";
            }
            out += "\n";
        }
        return out;
    }
}
