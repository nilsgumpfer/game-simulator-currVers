package basic;

import java.util.List;

public class MiddlePlayer extends Player {

    public MiddlePlayer()
    {
        name = "MiddlePlayer";
    }

	@Override
	Move nextMove(Position p, List<Move> moves) {
		return moves.get(moves.size()/2);
	}

}
