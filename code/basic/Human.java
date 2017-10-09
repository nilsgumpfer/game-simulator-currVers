package basic;

import java.util.List;

import lib.plotter.Sleep;

public class Human extends Player {

    public Human()
    {
        name = "Human";
    }

	@Override
	Move nextMove(Position p, List<Move> moves) {
		while (true) {
			String s = p.getXsend().abfragen();
			if (s.startsWith("#")) {
				String[] parts = s.split(" ");
				int x = Integer.parseInt(parts[2]);
				if (1 <= x & x <= 7) {
					return new Move(x);
				}
			}
			Sleep.sleep(20);
		}
	}

}
