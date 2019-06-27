package basic;

public class CentralRepo {
    static String delimiter                = "/"; //set to "\\" on Windows
    static int     IMG_WIDTH               = 70;
    static int     IMG_HEIGHT              = 70;
    static int     MY_IMG_WIDTH            = 64;
    static int     MY_IMG_HEIGHT           = 54;
    static int     IMG_CHANNELS            = 3;
    static int     MOD_KERAS_VERSION       = 1; //TODO: change version if necessary
    static String  MOD_KERAS_DIRECTORY     = "keras";
    static String  IMG_DIRECTORY           = "tmp";
    static String  IMG_FILENAME            = "currentGameboardState";
    static String  IMG_FILE_EXTENSION      = ".png";
    static String  IMG_PATH                = MOD_KERAS_DIRECTORY + delimiter + IMG_DIRECTORY + delimiter + IMG_FILENAME + IMG_FILE_EXTENSION;
    static String  MOD_JSON_PATH           = MOD_KERAS_DIRECTORY + delimiter + "KerasImageClassifier_Model_K"   + MOD_KERAS_VERSION + ".json";
    static String  MOD_WEIGHTS_PATH        = MOD_KERAS_DIRECTORY + delimiter + "KerasImageClassifier_Weights_K" + MOD_KERAS_VERSION + ".h5";
    static String  YOUR_MODEL_PATH         = MOD_KERAS_DIRECTORY + delimiter + "MyModel" + ".json";
    static String  YOUR_WEIGHTS_PATH       = MOD_KERAS_DIRECTORY + delimiter + "MyWeights" + ".h5";
    static String  MY_MODEL_PATH           = MOD_KERAS_DIRECTORY + delimiter + "connectfourmodel" + ".json";
    static String  MY_WEIGHTS_PATH         = MOD_KERAS_DIRECTORY + delimiter + "connectfourweights" + ".h5";

    //TODO: currently it works only with Keras Version 1.x -> 2.x support will be available in next dl4j versions
}
