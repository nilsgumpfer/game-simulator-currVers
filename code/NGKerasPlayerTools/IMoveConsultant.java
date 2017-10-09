package NGKerasPlayerTools;

import basic.Move;

import java.util.List;

/**
 * Created by Nils on 30.04.2017.
 */
public interface IMoveConsultant
{
    Move getBestPossibleMove(List<Move> possibleMoves);
}
