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
    public Piece(char type, char color, int currentI, int currentJ){
        this.type = type;
        this.color = color;
        factor = this.color == 'w'?-1:1; //blacks checking will be opposite of white so we make it negative
        this.currentI = currentI;
        this.currentJ = currentJ;
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
    public boolean checkIfEmptyInARow(int li, int lj, Piece[][] board){
        if(color == 'w') {
            for (int i = currentI + factor; i >= li; i--) {
                if (!checkIfEmpty(i, lj, board)) {
                    return false;
                }
            }
            for (int j = currentJ; j >= lj; j--) {
                if (!checkIfEmpty(li, j, board)) {
                    return false;
                }
            }
            return true;
        } else {
            for (int i = currentI + factor; i <= li; i++) {
                if (!checkIfEmpty(i, lj, board)) {
                    return false;
                }
            }
            for (int j = currentJ; j <= lj; j++) {
                if (!checkIfEmpty(li, j, board)) {
                    return false;
                }
            }
            return true;
        }
    }

    public void takePiece(int i, int j, Piece[][] board){
        if(!checkIfEmpty(i, j, board)){
            System.out.println("Taking (color,type)"+board[i][j].getColor()+","+board[i][j].getType());
        }
    }
    public boolean move(int i, int j, Piece[][] board){
        if(checkIfValidMove(i, j, board)) {
            moved = true;
            currentI = i;
            currentJ = j;
            return true;
        } else {
            return false;
        }
    }
    //given a certain i and j to move (remember i = y and j = x) check to see if valid move.
    public boolean checkIfTakeable(int i, int j, Piece[][] board){
        if(type == '_') return false; //can't move an empty square!
        if(color == board[i][j].getColor()) return false; //can't interact with same color pieces!
        else if(type == 'p'){
            if(Math.abs(j - currentJ) == 1){
                return !checkIfEmpty(i, j, board);
            }
        } else if(type == 'h'){
            if(Math.abs(i - currentI) == 1){ //sideways L, can move backwards
                if(Math.abs(j - currentJ) == 2){
                    return !checkIfEmpty(i, j, board);
                }
            }
            if(Math.abs(i - currentI) == 2){ //long L, can move backwards
                if(Math.abs(j - currentJ) == 1){
                    return !checkIfEmpty(i, j, board);
                }
            }
        }
        return false;
    }
    public boolean checkIfValidMove(int i, int j, Piece[][] board){
        //lots of if statements specifying valid moves specific to different types, i.e. knight only moves in an L.
        if(type == '_') return false; //can't move an empty square!
        if(color == board[i][j].getColor()) return false; //can't interact with same color pieces!
        else if(type == 'p' && !pawnValid(i, j, board)) return false; //pawn valid
        else if(type == 'h' && !knightValid(i, j, board)) return false; //knight valid
        return true;
    }

    public boolean pawnValid(int i, int j, Piece[][] board){
        if(i - currentI == 1*factor){ //if white, will check if dif is -1. if black, will check if dif is 1.
            if(currentJ == j) {
                return checkIfEmpty(i, j, board);
            } else if (checkIfTakeable(i, j, board)){
                takePiece(i, j, board);
                return true;
            }
        }
        if(i - currentI == 2*factor && !moved){
            if(currentJ == j) {
                return checkIfEmptyInARow(i, j, board);
            }
        }
        System.out.println("invalid "+type+" move at steps: " + (i - currentI) + " , " + (j - currentJ));
        return false;
    }
    public boolean knightValid(int i, int j, Piece[][] board){
        if(Math.abs(i - currentI) == 1){ //sideways L, can move backwards
            if(Math.abs(j - currentJ) == 2){
                takePiece(i, j, board);
                return true;
            }
        }
        if(Math.abs(i - currentI) == 2){ //long L, can move backwards
            if(Math.abs(j - currentJ) == 1){
                takePiece(i, j, board);
                return true;
            }
        }
        System.out.println("invalid "+type+" move at steps: " + (i - currentI) + " , " + (j - currentJ));
        return false;
    }
}
