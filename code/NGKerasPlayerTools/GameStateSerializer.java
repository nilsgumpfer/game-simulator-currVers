package NGKerasPlayerTools;

import basic.GUI;
import basic.Move;

import java.io.File;
import java.nio.file.FileSystemException;

import static NGKerasPlayerTools.CentralRepo.*;

public class GameStateSerializer {
    private static GameStateSerializer instance = new GameStateSerializer();
    private static int[] redIndex = {0,0,0,0,0,0,0};
    private static int[] blueIndex = {0,0,0,0,0,0,0};

    private GameStateSerializer(){}

    public static GameStateSerializer getInstance() {
        return instance;
    }

    public void serializeGameState(GUI gui, String player, String color, Move move) throws FileSystemException {
        String playerName   = player.replace(" ", ""); //condense string
        String subFolder    = playerName + color.toUpperCase() + "\\" + "col" + move.getS();
        String fileName     = "col" + move.getS() + "_";
        int currIndex       = 0;

        if(color.equals("RED")) {
            currIndex = redIndex[move.getS()-1];
            redIndex[move.getS()-1]++;
        }
        else if(color.equals("BLUE")) {
            currIndex = blueIndex[move.getS()-1];
            blueIndex[move.getS()-1]++;
        }

        fileName += currIndex;

        // check if write for specific folder just starts
        if(currIndex == 0)
        {
            // if so, initialize folder first
            File folder = new File(IMG_DIRECTORY + "\\" + subFolder);

            // check if folder exists
            if(folder.exists()) {
                // if present, clear content (first delete it, then re-create it)

                if(folder.listFiles() != null)
                    for (File c : folder.listFiles())
                        if(! c.delete())
                            throw new FileSystemException("Deletion of file " + c.getAbsolutePath() + " failed!");

                // ensure creation is successful, otherwise stop and throw exception
                if(! folder.delete())
                    throw new FileSystemException("Deletion of directory " + folder.getAbsolutePath() + " failed!");
            }

            // ensure creation is successful, otherwise stop and throw exception
            if(! folder.mkdirs())
                throw new FileSystemException("Creation of directory " + folder.getAbsolutePath() + " failed!");
        }

        // ..this here is more than important! This line of code ensures all symbols are redrawn before a screenshot/export is taken
        gui.getBoard().redrawSymbols();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveBoardStateAsImage(gui, MOD_KERAS_DIRECTORY, IMG_DIRECTORY + "\\" + subFolder, fileName, IMG_FILE_EXTENSION);
    }

    public static void saveBoardStateAsImage(GUI gui, String directory, String subFolder, String filename, String fileExtension) throws FileSystemException {

        File folder = new File(directory + "\\" + subFolder);

        // check if folder does not exist
        if(! folder.exists()) {
            // if so, create it

            // ensure creation is successful, otherwise stop and throw exception
            if(! folder.mkdirs())
                throw new FileSystemException("Creation of directory " + folder.getAbsolutePath() + " failed!");
        }

        gui.getBoard().getGraphic().saveImageToFile(directory + "\\" + subFolder + "\\" + filename + fileExtension, false);
    }
}
