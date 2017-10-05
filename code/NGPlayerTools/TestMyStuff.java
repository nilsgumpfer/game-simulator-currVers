package NGPlayerTools;

import basic.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 11.05.2017.
 */
public class TestMyStuff {
    public static void main(String args[])
    {
        AMoveConsultant moveConsultant = new FirstMoveConsultant();
        List<Move> moveList = new ArrayList<>();

        moveList.add(new Move(1));
        moveList.add(new Move(2));
        moveList.add(new Move(3));
        moveList.add(new Move(4));
        moveList.add(new Move(5));
        moveList.add(new Move(6));
        moveList.add(new Move(7));

        /*
        moveConsultant.incorporateOwnMove(new Move(2));
        moveConsultant.incorporateOwnMove(new Move(3));
        moveConsultant.incorporateRivalMove(new Move(4));
        moveConsultant.incorporateOwnMove(new Move(2));
        moveConsultant.incorporateRivalMove(new Move(3));
        moveConsultant.incorporateRivalMove(new Move(4));
        moveConsultant.incorporateOwnMove(new Move(3));
        moveConsultant.incorporateOwnMove(new Move(4));
        moveConsultant.incorporateOwnMove(new Move(4));
        */
        /*
        moveConsultant.incorporateRivalMove(new Move(1));
        moveConsultant.incorporateRivalMove(new Move(1));
        moveConsultant.incorporateOwnMove(new Move(1));

        moveConsultant.incorporateRivalMove(new Move(2));
        moveConsultant.incorporateRivalMove(new Move(2));
        moveConsultant.incorporateOwnMove(new Move(2));

        moveConsultant.incorporateOwnMove(new Move(3));
        moveConsultant.incorporateRivalMove(new Move(3));
        moveConsultant.incorporateOwnMove(new Move(3));

        moveConsultant.incorporateOwnMove(new Move(4));
        moveConsultant.incorporateOwnMove(new Move(4));
        moveConsultant.incorporateRivalMove(new Move(4));

        moveConsultant.incorporateRivalMove(new Move(5));
        moveConsultant.incorporateOwnMove(new Move(5));
        moveConsultant.incorporateRivalMove(new Move(5));
        */
        /*
        moveConsultant.incorporateOwnMove(new Move(1));

        moveConsultant.incorporateRivalMove(new Move(2));
        moveConsultant.incorporateOwnMove(new Move(2));

        moveConsultant.incorporateRivalMove(new Move(3));
        moveConsultant.incorporateRivalMove(new Move(3));
        moveConsultant.incorporateOwnMove(new Move(3));

        moveConsultant.incorporateRivalMove(new Move(4));
        moveConsultant.incorporateRivalMove(new Move(4));
        moveConsultant.incorporateRivalMove(new Move(4));
        */

        moveConsultant.incorporateRivalMove(new Move(1));
        moveConsultant.incorporateRivalMove(new Move(2));
        moveConsultant.incorporateRivalMove(new Move(3));

        moveConsultant.incorporateOwnMove(new Move(1));
        moveConsultant.incorporateOwnMove(new Move(2));
        moveConsultant.incorporateOwnMove(new Move(3));

        Move move = moveConsultant.getBestPossibleMove(moveList);

        //System.out.println(move);
    }
}
