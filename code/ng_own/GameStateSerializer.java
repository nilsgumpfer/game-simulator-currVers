package ng_own;

import basic.GUI;
import basic.Move;

public class GameStateSerializer {
    private String fileDestination = "\\img\\";
    private static GameStateSerializer instance = new GameStateSerializer();

    private GameStateSerializer(){}

    public static GameStateSerializer getInstance() {
        return instance;
    }

    public void serializeGameState(GUI gui, String player, String color, Move move) {
        String playerName = player.replace(" ", ""); //condense string
        String subFolder = color + "\\" + "col" + move.getS() + "\\";

        gui.getBoard().getGraphic().saveImageToFile(fileDestination + subFolder + playerName + System.currentTimeMillis() + ".png");
    }

    public void setFileDestination(String fileDestination) {
        this.fileDestination = fileDestination;
    }
}
