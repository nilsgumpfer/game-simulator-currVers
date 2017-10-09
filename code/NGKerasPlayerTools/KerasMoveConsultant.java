package NGKerasPlayerTools;

import basic.GUI;
import basic.Move;
import org.datavec.image.loader.NativeImageLoader;

import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Nils on 30.04.2017.
 */
public class KerasMoveConsultant implements IMoveConsultant {

    private int IMG_WIDTH = 70;
    private int IMG_HEIGHT = 70;
    private int IMG_CHANNELS = 3;
    private String IMG_DIRECTORY = "";
    private String IMG_FILENAME = "currentGameboardState";
    private String IMG_FILEEXTENSION = ".png";
    private String IMG_PATH = /*IMG_DIRECTORY + "\\" + */IMG_FILENAME + IMG_FILEEXTENSION;
    //private static String MOD_JSON_PATH = "simple_image_classification_fgb_architecture.json"; // Keras 2.x json-file
    private String MOD_JSON_PATH = "simple_image_classification_fgb_architecture_K1.json"; // Keras 1.x json-file
    //private static String MOD_WEIGHTS_PATH = "simple_image_classification_fgb_weights_run3.h5"; // Keras 2.x h5-file
    private String MOD_WEIGHTS_PATH = "simple_image_classification_fgb_weights_K1_run1.h5"; // Keras 1.x h5-file
    //TODO: currently it works only with Keras Version 1.x -> 2.x support will be available in next dl4j versions

    @Override
    public Move getBestPossibleMove(List<Move> possibleMoves)
    {
        // save current gameboard-state as PNG
        GameStateSerializer.saveBoardStateAsPNG(GUI.getCurrentInstance(), IMG_DIRECTORY, IMG_FILENAME);

        // use keras model to predict best move for current state (using PNG image)
        int move = getPredictionFromKerasModel();

        // check if the move is also valid
        move = checkIfMoveIsValid(move, possibleMoves);

        // and return a valid one in the end
        return new Move(move);
    }

    private int checkIfMoveIsValid(int move, List<Move> possibleMoves) {
        // check if predicted move is contained in list of allowed moves
        for(Move itm : possibleMoves)
        {
            // if so, return it
            if(itm.getS() == move) {
                System.out.println("valid move predicted: " + move);
                return move;
            }
        }

        System.out.println(">>>WARN>>> INVALID move predicted!: " + move);
        // if move is not contained, pick a valid one (most middle one)
        return possibleMoves.get(possibleMoves.size() / 2).getS();
    }

    private int getPredictionFromKerasModel()
    {
        // create the model to be usable in java
        MultiLayerNetwork network = null;

        try {
            // use the json-ized model exported from python to create the keras model here
            // also hand over the weights generated during training (this is the "knowledge-base" of the model)
            network = KerasModelImport.importKerasSequentialModelAndWeights(MOD_JSON_PATH, MOD_WEIGHTS_PATH);
        } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        }

        // load the previously saved PNG image representing the current gameboard-state..
        NativeImageLoader imageLoader = new NativeImageLoader(IMG_HEIGHT, IMG_WIDTH, IMG_CHANNELS);

        // ..and put it into a double-array (containing the RGB-values 0-255)
        INDArray image = null;
        try {
            image = imageLoader.asMatrix(new File(IMG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // use the keras model to predict the best move (from its point of viw / on what it learned during training)
        int[] prediction = network.predict(image);

        // return the predicted value (index start 0, so increase by one to fit game-simulator´s index-range)
        return prediction[0] + 1;
    }
}
