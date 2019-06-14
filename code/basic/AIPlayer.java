package basic;

import java.util.List;

/**
 * Created by Nils Gumpfer on 08.10.2017.
 */
public class AIPlayer extends Player
{
    private IMoveConsultant moveConsultant = new AIMoveConsultant();

    public AIPlayer(){
        this.name = "AIPlayer";
    }

    @Override
    Move nextMove(Position p, List<Move> moves) {
        return moveConsultant.getBestPossibleMove(moves);
    }
}

