package ng_own;

import basic.GUI;
import basic.Move;

import java.awt.*;

public class GameStateSerializer {
    private String fileDestination = "\\img\\";
    private static GameStateSerializer instance = new GameStateSerializer();
    private static int[] redIndex = {0,0,0,0,0,0,0};
    private static int[] blueIndex = {0,0,0,0,0,0,0};
    private static int counterTEST = 0;

    private GameStateSerializer(){}

    public static GameStateSerializer getInstance() {
        return instance;
    }

    public void serializeGameState(GUI gui, String player, String color, Move move) {
        String playerName = player.replace(" ", ""); //condense string
        String subFolder = playerName + color.toUpperCase() + "\\";/* + "col" + move.getS() + "\\";*/
        String fileName = "col" + move.getS() + "_";

        if(color.equals("RED")) {
            fileName += redIndex[move.getS()-1];
            redIndex[move.getS()-1]++;
        }
        else if(color.equals("BLUE")) {
            fileName += blueIndex[move.getS()-1];
            blueIndex[move.getS()-1]++;
        }

        fileName = "" + counterTEST;
        counterTEST++;

        // That´s more than IMPORTANT! ..ensures all symbols are redrawn before a screenshot/export is taken
        gui.getBoard().redrawSymbols();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gui.getBoard().getGraphic().saveImageToFile(fileDestination + subFolder + fileName + ".png", false);
    }

    public void setFileDestination(String fileDestination) {
        this.fileDestination = fileDestination;
    }
}
