package NGPlayerTools;

import basic.Move;

/**
 * Created by Nils on 30.04.2017.
 */
public class VirtualPosition {
    private int horizontalPosition;
    private int verticalPosition;
    private PlayerColor playerColor                 = PlayerColor.Empty; //initial state: empty
    private int moveIndex                           = -1; //initial value: invalid
    private boolean tmpIsPartOfVerticalPattern      = false;
    private boolean tmpIsPartOfHorizontalPattern    = false;
    private boolean tmpIsPartOfDiagonalPattern      = false;
    private boolean positionScanned                 = false;
    private int totalPotentialScore                 = 0;

    public VirtualPosition(int horizontalPosition, int verticalPosition) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public int getMoveIndex() {
        return moveIndex;
    }

    public void setMoveIndex(int moveIndex) {
        this.moveIndex = moveIndex;
    }

    public void flushPatternFlags(){
        tmpIsPartOfDiagonalPattern      = false;
        tmpIsPartOfHorizontalPattern    = false;
        tmpIsPartOfVerticalPattern      = false;
        positionScanned                 = false;
    }

    public boolean isTmpIsPartOfVerticalPattern() {
        return tmpIsPartOfVerticalPattern;
    }

    public void setTmpIsPartOfVerticalPattern(boolean tmpIsPartOfVerticalPattern) {
        this.tmpIsPartOfVerticalPattern = tmpIsPartOfVerticalPattern;
    }

    public boolean isTmpIsPartOfHorizontalPattern() {
        return tmpIsPartOfHorizontalPattern;
    }

    public void setTmpIsPartOfHorizontalPattern(boolean tmpIsPartOfHorizontalPattern) {
        this.tmpIsPartOfHorizontalPattern = tmpIsPartOfHorizontalPattern;
    }

    public boolean isTmpIsPartOfDiagonalPattern() {
        return tmpIsPartOfDiagonalPattern;
    }

    public void setTmpIsPartOfDiagonalPattern(boolean tmpIsPartOfDiagonalPattern) {
        this.tmpIsPartOfDiagonalPattern = tmpIsPartOfDiagonalPattern;
    }

    public boolean isPositionScanned() {
        return positionScanned;
    }

    public void setPositionScanned(boolean positionScanned) {
        this.positionScanned = positionScanned;
    }

    public void addPotentialScore(int potentialScore) {
        totalPotentialScore += potentialScore;
    }

    public int getTotalPotentialScore() {
        return totalPotentialScore;
    }

    public void resetPotentialScore(){
        totalPotentialScore = 0;
    }

    public Move generateMoveForThisPosition()
    {
        return new Move(horizontalPosition + 1);
    }
}
