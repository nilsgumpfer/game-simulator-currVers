package basic;

import java.nio.file.FileSystemException;
import java.util.List;

import jserver.ColorNames;
import NGKerasPlayerTools.GameStateSerializer;
import plotter.Sleep;

public class Game {
	static int sleepTime = 0; //original: 500
	boolean pause = false;

	Position position = new Position();
	GUI gui;

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public Position getPosition() {
		return position;
	}

	public static int getSleepTime() {
		return sleepTime;
	}

	public static void setSleepTime(int sleepTime) {
		//Game.sleepTime = sleepTime; //TODO: currently, do nothing
	}

	public Player play(Player[] players) {
	    int colorIndicator = 1;

		if (gui != null) {
			position.setXsend(gui.getXsend());
			gui.show(position);
		}

		for (;;) {
			for (Player player : players) {
				while( pause ) {
					Sleep.sleep(100);
				}
				List<Move> moves = position.getMoves();
				// //System.out.println(moves);
				if (moves.isEmpty()) {
					////System.out.println("No more moves");
					// No winner
					return null;
				}
				Move move = player.nextMove(new Position( position ), moves);

				// before anything is displayed or done with this move, persist current state for later analysis
                // current state BEFORE move is saved, including PLANNED move
                // correlation between current state and planned action will be available
                try {
                    GameStateSerializer.getInstance().serializeGameState(gui, player.getName(), ColorNames.getName(GUI.color(colorIndicator)), move);
                } catch (FileSystemException e) {
                    e.printStackTrace();
                }

                position.move(move);
				if (gui != null) {
					Sleep.sleep(sleepTime);
					gui.show(position);
				}
				if (position.isWin()) {
					////System.out.println("Win: " + move);
					return player;
				}
				position.nextPlayer();

				//used for toggling between both colors
				if(colorIndicator == 1)
				    colorIndicator = -1;
				else
				    colorIndicator = 1;
			}

		}

	}

	public void togglePause() {
		pause  = ! pause;
	}

}
