package pieces;

import game.Board;

public class Queen extends Piece  {
    public Queen(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whitequeen":"blackqueen");
    }

    @Override
    public boolean isValidMove(int i, int j) {
        return false;
    }
}
