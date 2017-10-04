package basic;

import java.util.List;

public class FirstPlayer extends Player {

    public FirstPlayer()
    {
        name = "FirstPlayer";
    }

	@Override
	Move nextMove(Position p, List<Move> moves) {
		return moves.get(0);
	}


}
