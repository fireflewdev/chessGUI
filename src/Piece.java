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

    //get the board
    public void getBoard(Piece[][] board){
        this.board = board;
    }

    //set the board
    public Piece[][] setBoard(){
        return board;
    }

    //given a certain i and j to move (remember i = y and j = x), move there if it is a valid move.
    public Piece[][] move(int i, int j){
       if(checkIfValidMove(i,j)){
           board[currentI][currentJ] = new Piece('_',currentI,currentJ);
           currentI = i;
           currentJ = j;
           board[i][j] = this;
       }
        return board;
    }

    //given a certain i and j to move (remember i = y and j = x) check to see if valid move.
    public boolean checkIfValidMove(int i, int j){
        return true;
    }
}
