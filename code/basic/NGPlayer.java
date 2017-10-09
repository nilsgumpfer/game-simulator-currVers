package basic;

import NGPlayerTools.FirstMoveConsultant;
import NGPlayerTools.AMoveConsultant;

import java.util.List;

/**
 * Created by Nils Gumpfer on 30.04.2017.
 */
public class NGPlayer extends Player {

    public NGPlayer(){
        this.name = "NGPlayer";
    }

    private AMoveConsultant moveConsultant = new FirstMoveConsultant();

    //Position p => Offers several info about the current game-status (it´s a copy, not a reference!)
    //List<Move> moves => List of next possible moves (e.g. one column already full, etc.)
    @Override
    Move nextMove(Position p, List<Move> moves) {
        moveConsultant.incorporateRivalMove(p.getLastMove());
        Move move = moveConsultant.getBestPossibleMove(moves);
        moveConsultant.incorporateOwnMove(move);
        return move;
    }
}
