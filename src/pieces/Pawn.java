package pieces;

import game.Board;

public class Pawn extends Piece {

    public Pawn(PieceColor color, int currentI, int currentJ, Board board) {
        super(color, currentI, currentJ, board);
        loadImage(color==PieceColor.WHITE?"whitepawn":"blackpawn");
    }

    public boolean isValidMove(int i, int j){
        //System.out.println("it got here");

        if(i - currentI == factor){ //if white, will check if dif is -1. if black, will check if dif is 1.
            if(currentJ == j) {
                return board.isEmpty(i, j);
            } else if(Math.abs(j - currentJ) == 1 && !board.isEmpty(i, j)){
                takePiece(i, j);
                return true;
            }
        }
        if(i - currentI == 2*factor && !moved){
            if(currentJ == j) {
                return checkIfEmptyInAColumn(currentI, currentJ, i);
            }
        }
        return false;
    }

    private boolean checkIfEmptyInAColumn(int fi, int j, int li){ //for the pawn
        int f = Math.min(fi, li);
        int l = Math.max(fi, li);
        for (int i = f; i <= l; i++) {
            if(i != fi) {
                if (!board.isEmpty(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
