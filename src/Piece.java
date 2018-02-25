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
    private char color; //colors: w, b, _. "_" is color for blank squares.
    public Piece(char type, char color, int currentI, int currentJ){
        this.type = type;
        this.color = color;
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

    //given a certain i and j to move (remember i = y and j = x) check to see if valid move.
    public boolean checkIfValidMove(int i, int j, Piece[][] board){
        //lots of if statements specifying valid moves specific to different types, i.e. knight only moves in an L.
        if(type == '_') return false; //can't move an empty square!
        return true;
    }
}
