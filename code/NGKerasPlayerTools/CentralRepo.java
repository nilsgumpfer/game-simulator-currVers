package NGKerasPlayerTools;

public class CentralRepo {
    static int     IMG_WIDTH               = 70;
    static int     IMG_HEIGHT              = 70;
    static int     IMG_CHANNELS            = 3;
    static int     MOD_KERAS_VERSION       = 1; //TODO: change version if necessary
    static String  MOD_KERAS_DIRECTORY     = "keras";
    static String  IMG_DIRECTORY           = "tmp";
    static String  IMG_FILENAME            = "currentGameboardState";
    static String  IMG_FILE_EXTENSION      = ".png";
    static String  IMG_PATH                = MOD_KERAS_DIRECTORY + "\\" + IMG_DIRECTORY + "\\" + IMG_FILENAME + IMG_FILE_EXTENSION;
    static String  MOD_JSON_PATH           = MOD_KERAS_DIRECTORY + "\\" + "KerasImageClassifier_Model_K"   + MOD_KERAS_VERSION + ".json";
    static String  MOD_WEIGHTS_PATH        = MOD_KERAS_DIRECTORY + "\\" + "KerasImageClassifier_Weights_K" + MOD_KERAS_VERSION + ".h5";

    //TODO: currently it works only with Keras Version 1.x -> 2.x support will be available in next dl4j versions
}
