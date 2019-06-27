package basic;

import java.util.List;

/**
 * Created by Nils Gumpfer on 08.10.2017.
 */
public class MyAIPlayer extends Player
{
    private IMoveConsultant moveConsultant = new MyAIMoveConsultant();

    public MyAIPlayer(){
        this.name = "MyAIPlayer";
    }

    @Override
    Move nextMove(Position p, List<Move> moves) {
        return moveConsultant.getBestPossibleMove(moves);
    }
}
