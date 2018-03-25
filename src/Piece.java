/*
  * different types of pieces
  * based on the param the piece will act a certain way. using char so its easier to manage
  * _ = empty piece
  * 1 king= Piece(k)
  * 1 queen = Piece(q)
  * 2 rooks = Piece(r)
  * 2 bishops = Piece(b)
  * 2 knights = Piece(h), changing to h (horse) so its easier to differentiate King from Knight as K and k look similar
  * 8 pawns = Piece(p)
  */
public class Piece{
    private boolean moved = false;
    private int currentI, currentJ; //current i an j values on board when piece invoked. remember i = y and j = x!
    private int factor;
    private char type; //type of piece, types explained above
    private char color; //colors: w, b, _. "_" is color for blank squares.
    private char taken; //which type taken
    public Piece(char type, char color, int currentI, int currentJ){
        this.type = type;
        this.color = color;
        this.currentI = currentI;
        this.currentJ = currentJ;
        taken = '_'; //nothing taken
        factor = this.color == 'w'?-1:1; //blacks checking will be opposite of white so we make it negative

    }

    //removed board editing stuff since that is now all done in the Game class.

    public char getType(){
        return type;
    }
    public char getColor(){
        return color;
    }

    public boolean checkIfEmpty(int i, int j, Piece[][] board){
        return board[i][j].getType() == '_';
    }
    public boolean checkIfEmptyInAColumnPawn(int fi, int j, int li, Piece[][] board){ //for the pawn
        int f = Math.min(fi, li);
        int l = Math.max(fi, li);
        for (int i = f; i <= l; i++) {
            if(i != fi) {
                if (!checkIfEmpty(i, j, board)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkIfEmptyInALine(int fi, int fj, int li, int lj, Piece[][] board){ //for everyone else
        int f = Math.min(fi, li);
        int l = Math.max(fi, li);
        for (int i = f; i <= l; i++) {
            if(i != fi &&  i != li) {
                if (!checkIfEmpty(i, fj, board)) {
                    return false;
                }
            }
        }
        f = Math.min(fj, lj);
        l = Math.max(fj, lj);
        for (int j = f; j <= l; j++) {
            if(j != fj &&  j != lj) {
                if (!checkIfEmpty(fi, j, board)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkIfEmptyInADiagonal(int fi, int fj, int li, int lj, Piece[][] board){ //for everyone else
        if(Math.abs(fi-li) != Math.abs(fj-lj)) return false;
        int smallI = Math.min(fi, li);
        int largeI = Math.max(fi, li);
        int smallJ = Math.min(fj, lj);
        int largeJ = Math.max(fj, lj);
        for (int i = smallI; i <= largeI; i++) {
            for(int j = smallJ; j <= largeJ; j++) {
                if (i != fi && i != li && j != fj && j != lj) {
                    if (!checkIfEmpty(i, j, board)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void takePiece(int i, int j, Piece[][] board){
        taken = board[i][j].getType();
    }
    public boolean move(int i, int j, Piece[][] board){
        if(checkIfValidMove(i, j, board)) {
            if(taken != '_') {
                System.out.println("taking " + taken);
            }
            moved = true;
            currentI = i;
            currentJ = j;
            return true;
        } else {
            System.out.println("invalid "+type+" move at steps: " + (i) + " , " + (j));
            return false;
        }
    }
    //given a certain i and j to move (remember i = y and j = x) check to see if valid move.

    public boolean checkIfValidMove(int i, int j, Piece[][] board){
        taken = '_';
        //lots of if statements specifying valid moves specific to different types, i.e. knight only moves in an L.
        if(type == '_') return false; //can't move an empty square!
        if(color == board[i][j].getColor()) return false; //can't interact with same color pieces!
        else if(type == 'p' && !pawnValid(i, j, board)) return false; //pawn valid
        else if(type == 'h' && !knightValid(i, j, board)) return false; //knight valid
        else if(type == 'r' && !rookValid(i, j, board)) return false; //rook valid
        else if(type == 'b' && !bishopValid(i, j, board)) return false; //rook valid

        return true;
    }

    public boolean pawnValid(int i, int j, Piece[][] board){
        //System.out.println("it got here");

        if(i - currentI == factor){ //if white, will check if dif is -1. if black, will check if dif is 1.
            if(currentJ == j) {
                return checkIfEmpty(i, j, board);
            } else if(Math.abs(j - currentJ) == 1 && !checkIfEmpty(i, j, board)){
                takePiece(i, j, board);
                return true;
            }
        }
        if(i - currentI == 2*factor && !moved){
            if(currentJ == j) {
                return checkIfEmptyInAColumnPawn(currentI, currentJ, i, board);
            }
        }
        return false;
    }
    public boolean knightValid(int i, int j, Piece[][] board){
        if(Math.abs(i - currentI) == 1){ //sideways L, can move backwards
            if(Math.abs(j - currentJ) == 2){
                if(!checkIfEmpty(i, j, board)) takePiece(i, j, board);
                return true;
            }
        }
        if(Math.abs(i - currentI) == 2){ //long L, can move backwards
            if(Math.abs(j - currentJ) == 1){
                if(!checkIfEmpty(i, j, board)) takePiece(i, j, board);
                return true;
            }
        }
        return false;
    }
    public boolean rookValid(int i, int j, Piece[][] board){
        if(currentI == i || currentJ == j) { //along same lines
            if (checkIfEmptyInALine(currentI, currentJ, i, j, board)) {
                if(!checkIfEmpty(i, j, board)) takePiece(i, j, board);
                return true;
            }
        }
        return false;
    }
    public boolean bishopValid(int i, int j, Piece[][] board){
        if(checkIfEmptyInADiagonal(currentI, currentJ, i, j, board)){
            if(!checkIfEmpty(i, j, board)) takePiece(i, j, board);
            return true;
        }
        return false;
    }
}
