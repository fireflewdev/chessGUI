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
    private int currentI, currentJ; //current i an j values on board when piece invoked. remember i = y and j = x!
    private char type; //type of piece, types explained above
    private Piece[][] board; //copy of the board sent to this piece
    public Piece(char type, int currentI, int currentJ){
        this.currentJ = currentJ;
        this.currentI = currentI;
        this.type = type;
    }

    //give the game your copy of the board
    public Piece[][] getBoard(){
        return board;
    }
    //set the game's board copy to yours
    public void setBoard(Piece[][] board){
        this.board = board;
    }
    public char getType(){
        return type;
    }

    //given a certain i and j to move (remember i = y and j = x), move there if it is a valid move.
    public void move(int i, int j){
       if(checkIfValidMove(i,j)){ //the secondI and secondJ validation should already account for this but just in case
           board[currentI][currentJ] = new Piece('_',currentI,currentJ);
           currentI = i;
           currentJ = j;
           board[i][j] = this;
       } else {
           System.out.println("an error with secondI,J validation!"); //if something weird happens it'll print this for debugging
       }
    }

    //given a certain i and j to move (remember i = y and j = x) check to see if valid move.
    public boolean checkIfValidMove(int i, int j){
        //lots of if statements specifying valid moves specific to different types, i.e. knight only moves in an L.
        if(type == '_') return false; //can't move an empty square!
        return true;
    }
}
