package NGKerasPlayerTools;

import basic.Move;
import org.datavec.image.loader.NativeImageLoader;

import org.deeplearning4j.nn.modelimport.keras.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nils on 30.04.2017.
 */
public class KerasMoveConsultant implements IMoveConsultant {

    private static int IMG_WIDTH = 70;
    private static int IMG_HEIGHT = 70;
    private static int IMG_CHANNELS = 3;
    private static String IMG_PATH = "currentGameboardState.png";
    //private static String MOD_JSON_PATH = "simple_image_classification_fgb_architecture.json"; // Keras 2.x json-file
    private static String MOD_JSON_PATH = "simple_image_classification_fgb_architecture_K1.json"; // Keras 1.x json-file
    //private static String MOD_WEIGHTS_PATH = "simple_image_classification_fgb_weights_run3.h5"; // Keras 2.x h5-file
    private static String MOD_WEIGHTS_PATH = "simple_image_classification_fgb_weights_K1_run1.h5"; // Keras 1.x h5-file
    //TODO: currently it works only with Keras Version 1.x -> 2.x support will be available in next dl4j versions

    @Override
    public Move getBestPossibleMove(List<Move> possibleMoves) {
        MultiLayerNetwork network = null;

        try {
            network = KerasModelImport.importKerasSequentialModelAndWeights(MOD_JSON_PATH, MOD_WEIGHTS_PATH);
        } catch (IOException | InvalidKerasConfigurationException | UnsupportedKerasConfigurationException e) {
            e.printStackTrace();
        }

        NativeImageLoader imageLoader = new NativeImageLoader(IMG_HEIGHT, IMG_WIDTH, IMG_CHANNELS);

        INDArray image = null;
        try {
            image = imageLoader.asMatrix(new File(IMG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] prediction = network.predict(image);

        return new Move(prediction[0] + 1);
    }

    public static void main(String[] args) {
        new KerasMoveConsultant().getBestPossibleMove(null);
    }
}
