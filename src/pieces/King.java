package pieces;

import game.Board;

public class King extends Piece {
    public King(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whiteking":"blackking");

    }

    @Override
    public boolean isValidMove(int i, int j) {
        return false;
    }
}
