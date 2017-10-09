package NGKerasPlayerTools;

import basic.GUI;
import basic.Move;
import org.datavec.image.loader.NativeImageLoader;

import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;

import static NGKerasPlayerTools.CentralRepo.*;

/**
 * Created by Nils on 30.04.2017.
 */
public class KerasMoveConsultant implements IMoveConsultant {
    private Logger log = LoggerFactory.getLogger("KerasMoveConsultant");
    private MultiLayerNetwork network;

    public KerasMoveConsultant()
    {
        initializeKerasModel();
    }

    @Override
    public Move getBestPossibleMove(List<Move> possibleMoves)
    {
        // save current gameboard-state as PNG
        try {
            GameStateSerializer.saveBoardStateAsImage(GUI.getCurrentInstance(), MOD_KERAS_DIRECTORY, IMG_DIRECTORY, IMG_FILENAME, IMG_FILE_EXTENSION);
        } catch (FileSystemException e) {
            e.printStackTrace();
        }

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
                log.info("valid move predicted: " + move);
                return move;
            }
        }

        log.warn("INVALID move predicted!: " + move);

        // if move is not contained, pick a valid one (most middle one)
        return possibleMoves.get(possibleMoves.size() / 2).getS();
    }

    private void initializeKerasModel()
    {
        // create the model to be usable in java
        try {
            // use the json-ized model exported from python to create the keras model here
            // also hand over the weights generated during training (this is the "knowledge-base" of the model)
            network = KerasModelImport.importKerasSequentialModelAndWeights(MOD_JSON_PATH, MOD_WEIGHTS_PATH);
        } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
            log.error("Following Exception occured when trying to import the Keras model and weights: ", e);
        }
    }

    private int getPredictionFromKerasModel()
    {
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
