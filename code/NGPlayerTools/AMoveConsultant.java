package NGPlayerTools;

import basic.Move;

import java.util.List;

/**
 * Created by Nils on 30.04.2017.
 */
public abstract class AMoveConsultant
{
    protected VirtualGameBoard virtualGameBoard = new VirtualGameBoard();

    public abstract void incorporateRivalMove(Move lastMove);
    public abstract void incorporateOwnMove(Move lastMove);
    public abstract Move getBestPossibleMove(List<Move> possibleMoves);

    protected void incorporateMove(Move move, PlayerColor playerColor){
        if(move != null) {
            int columnIndex = MyHelper.extractColumnIndex(move);
            virtualGameBoard.addCoinToColumn(columnIndex, playerColor);
        }
    }
}
