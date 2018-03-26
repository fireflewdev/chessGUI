package pieces;

import game.Board;

public class Blank extends Piece {
    public Blank(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
    }

    @Override
    public boolean isValidMove(int i, int j) {
        return false;
    }
}
