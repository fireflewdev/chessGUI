package pieces;

import game.Board;

public class Knight extends Piece {

    public Knight(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whiteknight":"blackknight");
    }

    public boolean isValidMove(int i, int j){
        if(Math.abs(i - currentI) == 1){ //sideways L, can move backwards
            if(Math.abs(j - currentJ) == 2){
                if(!board.isEmpty(i, j)) takePiece(i, j);
                return true;
            }
        }
        if(Math.abs(i - currentI) == 2){ //long L, can move backwards
            if(Math.abs(j - currentJ) == 1){
                if(!board.isEmpty(i, j)) takePiece(i, j);
                return true;
            }
        }
        return false;
    }
    public char toChar(){
        return color==PieceColor.WHITE?'N':'n';
    }
}
