package basic;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static basic.CentralRepo.YOUR_MODEL_PATH;
import static basic.CentralRepo.YOUR_WEIGHTS_PATH;


public class AIMoveConsultant implements IMoveConsultant {
    private Logger log = LoggerFactory.getLogger("AIMoveConsultant");
    private MultiLayerNetwork network;

    public AIMoveConsultant()
    {
        initializeKerasModel();
    }

    @Override
    public Move getBestPossibleMove(List<Move> possibleMoves)
    {
        //TODO: maybe more code necessary here for your implementation

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
            network = KerasModelImport.importKerasSequentialModelAndWeights(YOUR_MODEL_PATH, YOUR_WEIGHTS_PATH);
        } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
            log.error("Following Exception occured when trying to import the Keras model and weights: ", e);
        }
    }

    private int getPredictionFromKerasModel()
    {
        return 0; //TODO: Add your prediction code
    }
}
