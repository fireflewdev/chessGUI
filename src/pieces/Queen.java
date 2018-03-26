package pieces;

import game.Board;

public class Queen extends Piece  {
    public Queen(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whitequeen":"blackqueen");
    }

    @Override
    public boolean isValidMove(int i, int j) {
        if(currentI == i || currentJ == j) { //along same lines
            if (checkIfEmptyInALine(currentI, currentJ, i, j)) {
                if(!board.isEmpty(i, j)) takePiece(i, j);
                return true;
            }
        }
        if(checkIfEmptyInADiagonal(currentI, currentJ, i, j)){
            if(!board.isEmpty(i, j)) takePiece(i, j);
            return true;
        }
        return false;
    }
}
