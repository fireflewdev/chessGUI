package pieces;

import game.Board;

public class Rook extends Piece {

    public Rook(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whiterook":"blackrook");
    }

    public boolean isValidMove(int i, int j){
        if(currentI == i || currentJ == j) { //along same lines
            if (checkIfEmptyInALine(currentI, currentJ, i, j)) {
                if(!board.isEmpty(i, j)) takePiece(i, j);
                return true;
            }
        }
        return false;
    }
}
