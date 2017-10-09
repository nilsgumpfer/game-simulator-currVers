package NGKerasPlayerTools;

import basic.GUI;
import basic.Move;

public class GameStateSerializer {
    private String fileDestination = "\\img\\";
    private static GameStateSerializer instance = new GameStateSerializer();
    private static int[] redIndex = {327,262,448,7167,1064,312,442};
    private static int[] blueIndex = {0,0,0,0,0,0,0};
    private static int counterTEST = 0;

    private GameStateSerializer(){}

    public static GameStateSerializer getInstance() {
        return instance;
    }

    public void serializeGameState(GUI gui, String player, String color, Move move) {
        String playerName = player.replace(" ", ""); //condense string
        String subFolder = playerName + color.toUpperCase() + "\\" + "col" + move.getS() + "\\";
        String fileName = "col" + move.getS() + "_";

        if(color.equals("RED")) {
            fileName += redIndex[move.getS()-1];
            redIndex[move.getS()-1]++;
            System.out.println("File Counter for Red: " + counterTEST);
            counterTEST++;
        }
        else if(color.equals("BLUE")) {
            fileName += blueIndex[move.getS()-1];
            blueIndex[move.getS()-1]++;
        }

        //fileName = "" + counterTEST;
        //counterTEST++;

        // That´s more than just IMPORTANT! ..ensures all symbols are redrawn before a screenshot/export is taken
        gui.getBoard().redrawSymbols();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveBoardStateAsPNG(gui, fileDestination + subFolder, fileName);
    }

    public static void saveBoardStateAsPNG(GUI gui, String directory, String filename){
        gui.getBoard().getGraphic().saveImageToFile(directory + filename + ".png", false);
    }

    public void setFileDestination(String fileDestination) {
        this.fileDestination = fileDestination;
    }
}
