package NGPlayerTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 01.05.2017.
 */
public class VirtualPattern {
    private int chainLength;
    private VirtualPosition startPosition;
    private VirtualPosition endPosition;
    private PatternType patternType;
    private PlayerColor playerColor;
    private int potentialRating = -1;
    private VirtualGameBoard virtualGameBoard;
    private List<VirtualPotential> listOfPotentials = new ArrayList<>();
    private boolean isCompletableToFour;
    private ScanDirection scanDirection;

    public VirtualPattern(PatternType patternType, VirtualPosition startPosition, VirtualPosition endPosition, VirtualGameBoard virtualGameBoard, int chainLength, PlayerColor playerColor, ScanDirection scanDirection) {
        this.patternType        = patternType;
        this.startPosition      = startPosition;
        this.endPosition        = endPosition;
        this.virtualGameBoard   = virtualGameBoard;
        this.chainLength        = chainLength;
        this.playerColor        = playerColor;
        this.scanDirection      = scanDirection;
        listOfPotentials        = MyHelper.scanPotentialsOfPattern(this);
        this.patternType        = MyHelper.redefinePatternType(this);
        //potentialRating         = MyHelper.scorePotentialOfPattern(this);
        isCompletableToFour     = MyHelper.checkCompleteability(this);

        PotentialManager.incorporatePotentials(listOfPotentials);

        ////System.out.println("new Pattern:" + this);
    }

    public VirtualPosition getStartPosition() {
        return startPosition;
    }

    public VirtualPosition getEndPosition() {
        return endPosition;
    }

    public PatternType getPatternType() {
        return patternType;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public int getPotentialRating() {
        return potentialRating;
    }

    public List<VirtualPotential> getListOfPotentials() {
        return listOfPotentials;
    }

    public int getChainLength() {
        return chainLength;
    }

    public VirtualGameBoard getVirtualGameBoard() {
        return virtualGameBoard;
    }

    public boolean isCompletableToFour() {
        return isCompletableToFour;
    }

    public ScanDirection getScanDirection() {
        return scanDirection;
    }

    @Override
    public String toString(){
        return  "(" + startPosition.getHorizontalPosition() + "|" + startPosition.getVerticalPosition() + ")" +
                "(" + endPosition.getHorizontalPosition() + "|" + endPosition.getVerticalPosition() + ")" +
                patternType + " " + playerColor + " " + scanDirection + " " + isCompletableToFour;
    }
}
