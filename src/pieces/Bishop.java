package pieces;

import game.Board;

public class Bishop extends Piece {

    public Bishop(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whitebishop":"blackbishop");
    }

    public boolean isValidMove(int i, int j){
        if(checkIfEmptyInADiagonal(currentI, currentJ, i, j)){
            if(!board.isEmpty(i, j)) takePiece(i, j);
            return true;
        }
        return false;
    }
}
