package basic;

import NGKerasPlayerTools.IMoveConsultant;
import NGKerasPlayerTools.KerasMoveConsultant;

import java.util.List;

/**
 * Created by Nils Gumpfer on 08.10.2017.
 */
public class NGKerasPlayer extends Player {

    public NGKerasPlayer(){
        this.name = "NGKerasPlayer";
    }

    private IMoveConsultant moveConsultant = new KerasMoveConsultant();

    @Override
    Move nextMove(Position p, List<Move> moves) {
        return moveConsultant.getBestPossibleMove(moves);
    }
}
